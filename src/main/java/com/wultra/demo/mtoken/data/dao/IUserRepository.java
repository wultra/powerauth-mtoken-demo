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
package com.wultra.demo.mtoken.data.dao;

import com.wultra.demo.mtoken.data.entity.User;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface IUserRepository extends CrudRepository<User, UUID> {
    Optional<User> findByAccessToken(String accessToken);

    Optional<User> findByEmail(String email);

    Optional<User> findByVerificationCode(UUID verificationCode);
}
