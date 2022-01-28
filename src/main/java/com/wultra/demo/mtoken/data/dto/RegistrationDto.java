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

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationDto {
    @Size(min = 1)
    @Schema(description = "A string for the activation QR code. If you want to allow manual code entry as well in your app, you can split the string by the # character and use the first part of the split. The part after the # is data signature that helps the mobile device recognize an authentic QR code data while scanning the code.", example = "23456-DEFGH-77777-77777#MEUCIDFFx60vcqDcqRY014uE+5CH38lJ/NOVNoaGHWpE+7fVAiEA8LsWi9AvRJPwRnlSSOB3O080mXd6YbRt74DLQqrrLlg=")
    private String activationQrCodeData;

    @Size(min = 1, max = 255)
    @Schema(description = "The value representing cryptographic material exchanged with the Mobile Token.", example = "52649917")
    private String activationFingerprint;

    @NotNull
    @Schema(description = "The registered user.")
    private UserDto user;

    public String getActivationFingerprint() {
        return activationFingerprint;
    }

    public void setActivationFingerprint(String activationFingerprint) {
        this.activationFingerprint = activationFingerprint;
    }

    public String getActivationQrCodeData() {
        return activationQrCodeData;
    }

    public void setActivationQrCodeData(String activationQrCodeData) {
        this.activationQrCodeData = activationQrCodeData;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
