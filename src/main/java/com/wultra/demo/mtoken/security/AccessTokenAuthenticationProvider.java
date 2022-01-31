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
package com.wultra.demo.mtoken.security;

import com.wultra.demo.mtoken.data.entity.User;
import com.wultra.demo.mtoken.data.entity.UserStatus;
import com.wultra.demo.mtoken.service.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.CredentialsExpiredException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.time.OffsetDateTime;

@Component
public class AccessTokenAuthenticationProvider implements AuthenticationProvider {
    private final UserService userService;

    public AccessTokenAuthenticationProvider(UserService userService) {
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) {
        User user = userService
                .readByAccessToken(authentication.getCredentials().toString())
                .orElseThrow(() -> new BadCredentialsException("Unknown access token"));
        if (user.getStatus() != UserStatus.ACTIVE) {
            throw new DisabledException("User is not active");
        } else if (user.getAccessTokenExpires().isBefore(OffsetDateTime.now())) {
            throw new CredentialsExpiredException("Access token has expired");
        }

        UserAuthentication userAuthentication = new UserAuthentication(user);
        userAuthentication.setAuthenticated(true);
        return userAuthentication;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return BearerAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
