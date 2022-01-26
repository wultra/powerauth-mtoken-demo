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
package com.wultra.demo.mtoken.service;

import com.wultra.demo.mtoken.data.dao.IUserRepository;
import com.wultra.demo.mtoken.data.dto.NewUserDto;
import com.wultra.demo.mtoken.data.entity.User;
import com.wultra.demo.mtoken.data.mapper.UserMapper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class UserService {
    private final UserMapper userMapper;
    private final IUserRepository userRepository;

    public UserService(UserMapper userMapper, IUserRepository userRepository) {
        this.userMapper = userMapper;
        this.userRepository = userRepository;
    }

    public User create(NewUserDto newUserDto, UUID verificationCode) {
        User user = userMapper.toUser(newUserDto);
        user.setId(UUID.randomUUID());
        user.setVerificationCode(verificationCode);
        return userRepository.save(user);
    }

    public Optional<User> readByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> readByVerificationCode(UUID verificationCode) {
        return userRepository.findByVerificationCode(verificationCode);
    }

    public User update(User user) {
        return userRepository.save(user);
    }
}
