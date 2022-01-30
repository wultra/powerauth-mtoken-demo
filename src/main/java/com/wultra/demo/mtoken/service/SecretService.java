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

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Base64;
import java.util.UUID;

@Service
public class SecretService {
    private final int accessTokenBytes;
    private final SecureRandom strongSecureRandom;

    public SecretService(@Value("${accesstoken.bytes}") int accessTokenBytes, SecureRandom strongSecureRandom) {
        this.accessTokenBytes = accessTokenBytes;
        this.strongSecureRandom = strongSecureRandom;
    }

    public String generateAccessToken() {
        byte[] randomBytes = new byte[accessTokenBytes];
        strongSecureRandom.nextBytes(randomBytes);
        return Base64.getUrlEncoder().encodeToString(randomBytes);
    }

    public UUID generateEmailVerificationCode() {
        return UUID.randomUUID();
    }
}
