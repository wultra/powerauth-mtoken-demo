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
package com.wultra.mtoken.rest.service;

import com.wultra.mtoken.rest.data.dto.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Service
public class WultraMtokenService {
    private final RestTemplate wultraMtokenRestTemplate;

    public WultraMtokenService(RestTemplate wultraMtokenRestTemplate) {
        this.wultraMtokenRestTemplate = wultraMtokenRestTemplate;
    }

    public void commitRegistration(CommitRegistrationDto commitRegistrationDto) {
        wultraMtokenRestTemplate.postForObject("/registration/commit", commitRegistrationDto, Object.class);
    }

    public OperationStatusDto createOperation(NewOperationDto newOperationDto) {
        return wultraMtokenRestTemplate.postForObject("/operations", newOperationDto, OperationStatusDto.class);
    }

    public String createRegistration(NewRegistrationDto newRegistrationDto) {
        ActivationQrCodeDataDto activationQrCodeDataDto = wultraMtokenRestTemplate.postForObject("/registration", newRegistrationDto, ActivationQrCodeDataDto.class);
        return activationQrCodeDataDto.getActivationQrCodeData();
    }

    public OperationStatusDto getOperation(String operationId) {
        String url = UriComponentsBuilder
                .fromPath("/operations")
                .queryParam("operationId", operationId)
                .build()
                .toUriString();
        return wultraMtokenRestTemplate.getForObject(url, OperationStatusDto.class);
    }

    public RegistrationStatusDto getRegistration(String userId) {
        String url = UriComponentsBuilder
                .fromPath("/registration")
                .queryParam("userId", userId)
                .build()
                .toUriString();
        return wultraMtokenRestTemplate.getForObject(url, RegistrationStatusDto.class);
    }
}
