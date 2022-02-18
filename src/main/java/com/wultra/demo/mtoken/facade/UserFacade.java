/*
 * Wultra Mobile Token Demo
 * Copyright 2022 Wultra s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wultra.demo.mtoken.facade;

import com.wultra.demo.mtoken.data.dto.*;
import com.wultra.demo.mtoken.data.entity.Device;
import com.wultra.demo.mtoken.data.entity.User;
import com.wultra.demo.mtoken.data.entity.UserStatus;
import com.wultra.demo.mtoken.data.mapper.DeviceMapper;
import com.wultra.demo.mtoken.data.mapper.OperationMapper;
import com.wultra.demo.mtoken.data.mapper.UserMapper;
import com.wultra.demo.mtoken.exception.EmailException;
import com.wultra.demo.mtoken.service.DeviceService;
import com.wultra.demo.mtoken.service.IEmailService;
import com.wultra.demo.mtoken.service.SecretService;
import com.wultra.demo.mtoken.service.UserService;
import com.wultra.mtoken.rest.data.dto.*;
import com.wultra.mtoken.rest.service.WultraMtokenService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.OffsetDateTime;
import java.util.Optional;
import java.util.UUID;

@Component
public class UserFacade {
    private final long accessTokenValidityInSeconds;
    private final DeviceMapper deviceMapper;
    private final DeviceService deviceService;
    private final IEmailService emailService;
    private final OperationMapper operationMapper;
    private final SecretService secretService;
    private final UserMapper userMapper;
    private final UserService userService;
    private final WultraMtokenService wultraMtokenService;

    public UserFacade(
            @Value("${accesstoken.validity}") long accessTokenValidityInSeconds,
            DeviceMapper deviceMapper,
            DeviceService deviceService,
            IEmailService emailService,
            OperationMapper operationMapper,
            SecretService secretService,
            UserMapper userMapper,
            UserService userService,
            WultraMtokenService wultraMtokenService
    ) {
        this.accessTokenValidityInSeconds = accessTokenValidityInSeconds;
        this.deviceMapper = deviceMapper;
        this.deviceService = deviceService;
        this.emailService = emailService;
        this.operationMapper = operationMapper;
        this.secretService = secretService;
        this.userMapper = userMapper;
        this.userService = userService;
        this.wultraMtokenService = wultraMtokenService;
    }

    public RegistrationDto register(NewUserDto newUserDto) throws EmailException, IOException {
        Optional<User> optionalUser = userService.readByEmail(newUserDto.getEmail());
        if (optionalUser.isPresent()) {
            return userMapper.toRegistrationDto(optionalUser.get());
        }

        UUID emailVerificationCode = secretService.generateEmailVerificationCode();

        User user = userService.create(newUserDto, emailVerificationCode);

        emailService.sendEmailVerification(user);

        return userMapper.toRegistrationDto(user);
    }

    public RegistrationDto resendEmailVerification(String email) throws EmailException, IOException {
        Optional<User> optionalUser = userService.readByEmail(email);
        if (optionalUser.isEmpty()) {
            return null;
        }

        UUID emailVerificationCode = secretService.generateEmailVerificationCode();

        User user = optionalUser.get();
        user.setVerificationCode(emailVerificationCode);
        user = userService.update(user);

        emailService.sendEmailVerification(user);

        return userMapper.toRegistrationDto(user);
    }

    public RegistrationDto confirmEmail(UUID emailVerificationCode) {
        Optional<User> optionalUser = userService.readByVerificationCode(emailVerificationCode);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        user.setStatus(UserStatus.CREATED);
        user.setVerificationCode(null);
        user = userService.update(user);

        return userMapper.toRegistrationDto(user);
    }

    public RegistrationDto activateToken(String email) {
        Optional<User> optionalUser = userService.readByEmail(email);
        if (optionalUser.isEmpty()) {
            return null;
        }

        User user = optionalUser.get();
        if (user.getStatus() == UserStatus.PENDING) {
            return userMapper.toRegistrationDto(user);
        }

        RegistrationStatusDto registrationStatusDto = wultraMtokenService.getRegistration(user.getId().toString());
        if (registrationStatusDto.getRegistration() == Registration.NONE) {
            NewRegistrationDto newRegistrationDto = new NewRegistrationDto();
            newRegistrationDto.setUserId(user.getId().toString());
            String activationQrCodeData = wultraMtokenService.createRegistration(newRegistrationDto);

            return userMapper.toRegistrationDto(activationQrCodeData, user);
        }
        if (registrationStatusDto.getRegistration() != Registration.PENDING_COMMIT) {
            return userMapper.toRegistrationDto(registrationStatusDto.getActivationQrCodeData(), user);
        }

        CommitRegistrationDto commitRegistrationDto = new CommitRegistrationDto();
        commitRegistrationDto.setUserId(user.getId().toString());
        wultraMtokenService.commitRegistration(commitRegistrationDto);

        NewDeviceDto newDeviceDto = deviceMapper.toNewDeviceDto(registrationStatusDto);
        Device device = deviceService.create(user, newDeviceDto);

        user.setStatus(UserStatus.ACTIVE);
        user = userService.update(user);

        return userMapper.toRegistrationDto(device);
    }

    public LoginOperationDto login(String email) {
        Optional<User> optionalUser = userService.readByEmail(email);
        if (optionalUser.isEmpty() || optionalUser.get().getStatus() != UserStatus.ACTIVE) {
            return null;
        }

        User user = optionalUser.get();

        NewOperationDto newOperationDto = operationMapper.toNewOperationDto(user, OperationTemplate.login);
        OperationStatusDto operationStatusDto = wultraMtokenService.createOperation(newOperationDto);

        user.setOperationId(operationStatusDto.getOperationId());
        user = userService.update(user);

        return operationMapper.toLoginOperationDto(operationStatusDto, user);
    }

    public LoginOperationDto checkLogin(String email) {
        Optional<User> optionalUser = userService.readByEmail(email);
        if (optionalUser.isEmpty() || optionalUser.get().getStatus() != UserStatus.ACTIVE) {
            return null;
        }

        User user = optionalUser.get();

        OperationStatusDto operationStatusDto = wultraMtokenService.getOperation(user.getOperationId().toString());
        if (
                operationStatusDto.getOperationType() != OperationType.login ||
                operationStatusDto.getStatus() != OperationStatus.APPROVED
        ) {
            return operationMapper.toLoginOperationDto(operationStatusDto, user);
        }

        String accessToken = secretService.generateAccessToken();
        user.setOperationId(null);
        user.setAccessToken(accessToken);
        user.setAccessTokenExpires(OffsetDateTime.now().plusSeconds(accessTokenValidityInSeconds));
        user = userService.update(user);

        return operationMapper.toLoginOperationDto(operationStatusDto, user);
    }

    public void logout(User user) {
        user.setAccessToken(null);
        user.setAccessTokenExpires(null);
        user = userService.update(user);
    }
}
