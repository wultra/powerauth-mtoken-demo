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
package com.wultra.demo.mtoken.facade;

import com.wultra.demo.mtoken.data.dto.NewTransactionDto;
import com.wultra.demo.mtoken.data.dto.TransactionOperationDto;
import com.wultra.demo.mtoken.data.entity.User;
import com.wultra.demo.mtoken.data.mapper.OperationMapper;
import com.wultra.demo.mtoken.service.UserService;
import com.wultra.mtoken.rest.data.dto.NewOperationDto;
import com.wultra.mtoken.rest.data.dto.OperationStatusDto;
import com.wultra.mtoken.rest.service.WultraMtokenService;
import org.springframework.stereotype.Component;

@Component
public class TransactionFacade {
    private final OperationMapper operationMapper;
    private final UserService userService;
    private final WultraMtokenService wultraMtokenService;

    public TransactionFacade(OperationMapper operationMapper, UserService userService, WultraMtokenService wultraMtokenService) {
        this.operationMapper = operationMapper;
        this.userService = userService;
        this.wultraMtokenService = wultraMtokenService;
    }

    public TransactionOperationDto create(User user, NewTransactionDto newTransactionDto) {
        NewOperationDto newOperationDto = operationMapper.toNewOperationDto(user, newTransactionDto);
        OperationStatusDto operationStatusDto = wultraMtokenService.createOperation(newOperationDto);

        user.setOperationId(operationStatusDto.getOperationId());
        user = userService.update(user);

        return operationMapper.toTransactionOperationDto(operationStatusDto, user);
    }

    public TransactionOperationDto check(User user) {
        OperationStatusDto operationStatusDto = wultraMtokenService.getOperation(user.getOperationId().toString());

        return operationMapper.toTransactionOperationDto(operationStatusDto, user);
    }
}
