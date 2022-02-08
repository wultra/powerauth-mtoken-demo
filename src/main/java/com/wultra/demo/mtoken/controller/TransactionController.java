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
package com.wultra.demo.mtoken.controller;

import com.wultra.demo.mtoken.data.dto.NewTransactionDto;
import com.wultra.demo.mtoken.data.dto.TransactionOperationDto;
import com.wultra.demo.mtoken.data.entity.User;
import com.wultra.demo.mtoken.facade.TransactionFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Transactions")
public class TransactionController {
    private final TransactionFacade transactionFacade;

    public TransactionController(TransactionFacade transactionFacade) {
        this.transactionFacade = transactionFacade;
    }

    @PostMapping(path = "/transaction", consumes = "application/json", produces = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Request an approval to perform a transaction on behalf of a registered user.",
            description = "Creates a transaction operation that needs to be reviewed and either approved or rejected via the user's Mobile Token.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "201", description = "The approval has been requested."),
                    @ApiResponse(responseCode = "403", description = "The given access token has not been issued or has expired.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "An unexpected server condition has been encountered.", content = @Content(mediaType = "application/json"))
            }
    )
    public TransactionOperationDto create(Authentication authentication, @RequestBody NewTransactionDto newTransaction) {
        return transactionFacade.create((User) authentication.getPrincipal(), newTransaction);
    }

    @GetMapping(path = "/transaction", produces = "application/json")
    @Operation(
            summary = "Check the status of the user's transaction.",
            description = "This endpoint is intended to be polled regularly until the status of the operation is not PENDING.",
            security = @SecurityRequirement(name = "bearerAuth"),
            responses = {
                    @ApiResponse(responseCode = "200", description = "The operation has been checked."),
                    @ApiResponse(responseCode = "403", description = "The given access token has not been issued or has expired.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "An unexpected server condition has been encountered.", content = @Content(mediaType = "application/json"))
            }
    )
    public TransactionOperationDto check(Authentication authentication) {
        return transactionFacade.check((User) authentication.getPrincipal());
    }
}
