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
package com.wultra.demo.mtoken.data.mapper;

import com.wultra.demo.mtoken.data.dto.LoginOperationDto;
import com.wultra.demo.mtoken.data.dto.NewTransactionDto;
import com.wultra.demo.mtoken.data.dto.TransactionOperationDto;
import com.wultra.demo.mtoken.data.entity.User;
import com.wultra.mtoken.rest.data.dto.NewOperationDto;
import com.wultra.mtoken.rest.data.dto.OperationStatusDto;
import com.wultra.mtoken.rest.data.dto.OperationTemplate;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.math.BigDecimal;
import java.util.Map;

@Mapper(componentModel = "spring")
public abstract class OperationMapper {
    @Mapping(target = "operationId", source = "operationStatusDto.operationId")
    @Mapping(target = "status", source = "operationStatusDto.status")
    @Mapping(target = "user", source = "user")
    public abstract LoginOperationDto toLoginOperationDto(OperationStatusDto operationStatusDto, User user);

    public NewOperationDto toNewOperationDto(User user, NewTransactionDto newTransactionDto) {
        NewOperationDto newOperationDto = toNewOperationDto(user, OperationTemplate.authorize_payment);
        newOperationDto.setParameters(
                Map.of(
                        "amount", newTransactionDto.getAmount().toPlainString(),
                        "iban", newTransactionDto.getIban()
                )
        );
        return newOperationDto;
    }

    @Mapping(target = "userId", expression = "java( user.getId().toString() )")
    public abstract NewOperationDto toNewOperationDto(User user, OperationTemplate template);

    public TransactionOperationDto toTransactionOperationDto(OperationStatusDto operationStatusDto, User user) {
        TransactionOperationDto transactionOperationDto = toTransactionOperationDtoInternal(operationStatusDto, user);
        transactionOperationDto.setAmount(new BigDecimal(operationStatusDto.getParameters().get("amount")));
        transactionOperationDto.setIban(operationStatusDto.getParameters().get("iban"));
        return transactionOperationDto;
    }

    @Mapping(target = "operationId", source = "operationStatusDto.operationId")
    @Mapping(target = "status", source = "operationStatusDto.status")
    protected abstract TransactionOperationDto toTransactionOperationDtoInternal(OperationStatusDto operationStatusDto, User user);
}
