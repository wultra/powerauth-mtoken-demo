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
package com.wultra.mtoken.rest.data.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class RegistrationStatusDto {
    @NotNull
    private Registration registration;

    @Size(min = 1)
    private String activationQrCodeData;
    
    private String name;

    @Size(min = 1)
    private String platform;

    @Size(min = 1)
    private String deviceInfo;

    @Size(min = 1)
    private String activationFingerprint;

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

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlatform() {
        return platform;
    }

    public void setPlatform(String platform) {
        this.platform = platform;
    }

    public Registration getRegistration() {
        return registration;
    }

    public void setRegistration(Registration registration) {
        this.registration = registration;
    }
}
