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
import org.springframework.security.authentication.AbstractAuthenticationToken;

public class UserAuthentication extends AbstractAuthenticationToken {
    private User principal;

    public UserAuthentication(User principal) {
        super(null);
        this.principal = principal;
    }

    @Override
    public Object getCredentials() {
        return principal.getAccessToken();
    }

    @Override
    public String getName() {
        return principal.getId().toString();
    }

    @Override
    public Object getPrincipal() {
        return principal;
    }
}
