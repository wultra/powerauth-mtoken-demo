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
package com.wultra.demo.mtoken.data.dto;

import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Size;
import java.time.OffsetDateTime;

public class LoginOperationDto extends OperationDto {
    @Size(min = 32, max = 1024)
    @Schema(description = "An access token that can be used to make API requests on behalf of the user.", example = "8R0MSSoHTXghxUma5YXATMmOSv7nEUHZyyI9Q2rbJMg=")
    private String accessToken;

    @Schema(description = "When the access token will expire.", example = "2021-12-31T23:59:59.000001Z")
    private OffsetDateTime accessTokenExpires;

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public OffsetDateTime getAccessTokenExpires() {
        return accessTokenExpires;
    }

    public void setAccessTokenExpires(OffsetDateTime accessTokenExpires) {
        this.accessTokenExpires = accessTokenExpires;
    }
}
