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

import com.wultra.demo.mtoken.data.entity.UserStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.UUID;

public class UserDto {
    @NotNull
    @Schema(description = "The user's unique identifier.", example = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6")
    private UUID id;

    @NotBlank
    @Email
    @Size(min = 5, max = 255)
    @Schema(description = "The user's email address.", example = "john.doe@example.com")
    private String email;

    @NotBlank
    @Size(min = 1, max = 255)
    @Schema(description = "The user's name.", example = "John")
    private String name;

    @NotBlank
    @Size(min = 1, max = 255)
    @Schema(description = "The user's surname.", example = "Doe")
    private String surname;

    @NotNull
    @Schema(description = "The user's status. If PENDING, the user's email address needs to be verified. If CREATED, the user's Mobile Token needs to be activated. If ACTIVE, the user's registration was completed.", example = "ACTIVE")
    private UserStatus status;

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UserStatus getStatus() {
        return status;
    }

    public void setStatus(UserStatus status) {
        this.status = status;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }
}
