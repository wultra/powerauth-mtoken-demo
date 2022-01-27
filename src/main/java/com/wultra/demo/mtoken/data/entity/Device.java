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
package com.wultra.demo.mtoken.data.entity;

import javax.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "device")
public class Device {
    @ManyToOne
    @JoinColumn(name = "\"user\"", nullable = false)
    private User user;

    @Id
    @Column(nullable = false)
    private UUID id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String platform;

    @Column(name = "device_info", nullable = false)
    private String deviceInfo;

    @Column(name = "activation_fingerprint", nullable = false)
    private String activationFingerprint;

    public String getActivationFingerprint() {
        return activationFingerprint;
    }

    public void setActivationFingerprint(String activationFingerprint) {
        this.activationFingerprint = activationFingerprint;
    }

    public String getDeviceInfo() {
        return deviceInfo;
    }

    public void setDeviceInfo(String deviceInfo) {
        this.deviceInfo = deviceInfo;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
