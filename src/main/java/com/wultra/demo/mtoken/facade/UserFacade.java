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

import com.wultra.demo.mtoken.data.dto.NewUserDto;
import com.wultra.demo.mtoken.data.dto.UserDto;
import com.wultra.demo.mtoken.exception.EmailException;
import com.wultra.demo.mtoken.service.IEmailService;
import com.wultra.demo.mtoken.service.SecretService;
import com.wultra.demo.mtoken.service.UserService;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.UUID;

@Component
public class UserFacade {
    private final IEmailService emailService;
    private final SecretService secretService;
    private final UserService userService;

    public UserFacade(IEmailService emailService, SecretService secretService, UserService userService) {
        this.emailService = emailService;
        this.secretService = secretService;
        this.userService = userService;
    }

    public UserDto register(NewUserDto newUserDto) throws EmailException, IOException {
        UserDto userDto = userService.create(newUserDto);

        UUID emailVerificationCode = secretService.generateEmailVerificationCode();

        emailService.sendEmailVerification(userDto, emailVerificationCode.toString());

        return userDto;
    }
}