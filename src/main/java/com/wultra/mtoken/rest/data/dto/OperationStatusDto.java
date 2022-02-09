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

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.time.OffsetDateTime;
import java.util.Map;
import java.util.UUID;

public class OperationStatusDto {
    @NotNull
    private UUID operationId;

    @NotBlank
    private String userId;

    @NotNull
    private OperationStatus status;

    @NotNull
    private String operationType;

    private Map<String, String> parameters;

    @Positive
    private long failureCount;

    @Positive
    private long maxFailureCount;

    private OffsetDateTime timestampCreated;

    private OffsetDateTime timestampExpires;

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

    public String getOperationType() {
        return operationType;
    }

    public void setOperationType(String operationType) {
        this.operationType = operationType;
    }

    public Map<String, String> getParameters() {
        return parameters;
    }

    public void setParameters(Map<String, String> parameters) {
        this.parameters = parameters;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
