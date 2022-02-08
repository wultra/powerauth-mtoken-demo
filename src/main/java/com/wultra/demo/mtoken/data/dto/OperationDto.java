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

import com.wultra.mtoken.rest.data.dto.OperationStatus;
import io.swagger.v3.oas.annotations.media.Schema;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;
import java.util.UUID;

public class OperationDto {
    @NotNull
    @Schema(description = "The operation's unique identifier.", example = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6")
    private UUID operationId;

    @NotNull
    @Schema(description = "The operation's status. If PENDING, it is waiting for the user’s action. If CANCELED, it has been cancelled. If EXPIRED, it has expired. If APPROVED, it has been approved by the user. If REJECTED, it has been rejected by the user. If FAILED, it has failed due to too many failed approval attempts.", example = "PENDING")
    private OperationStatus status;

    @Positive
    @Schema(description = "How many failed approval attempts user has made.", example = "0", minimum = "0", required = true)
    private long failureCount;

    @Positive
    @Schema(description = "How many failed approval attempts are allowed for this operation.", example = "5", minimum = "0", required = true)
    private long maxFailureCount;

    @NotNull
    @Schema(description = "When the operation was created.", example = "2021-12-31T23:59:59.01Z")
    private OffsetDateTime timestampCreated;

    @NotNull
    @Schema(description = "When the operation will expire.", example = "2021-12-31T23:59:59.02Z")
    private OffsetDateTime timestampExpires;

    @NotNull
    @Schema(description = "The user that has requested the operation.")
    private UserDto user;

    public long getFailureCount() {
        return failureCount;
    }

    public void setFailureCount(long failureCount) {
        this.failureCount = failureCount;
    }

    public long getMaxFailureCount() {
        return maxFailureCount;
    }

    public void setMaxFailureCount(long maxFailureCount) {
        this.maxFailureCount = maxFailureCount;
    }

    public UUID getOperationId() {
        return operationId;
    }

    public void setOperationId(UUID operationId) {
        this.operationId = operationId;
    }

    public OperationStatus getStatus() {
        return status;
    }

    public void setStatus(OperationStatus status) {
        this.status = status;
    }

    public OffsetDateTime getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(OffsetDateTime timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public OffsetDateTime getTimestampExpires() {
        return timestampExpires;
    }

    public void setTimestampExpires(OffsetDateTime timestampExpires) {
        this.timestampExpires = timestampExpires;
    }

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}
