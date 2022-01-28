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

import com.wultra.demo.mtoken.data.dto.EmailDto;
import com.wultra.demo.mtoken.data.dto.NewUserDto;
import com.wultra.demo.mtoken.data.dto.RegistrationDto;
import com.wultra.demo.mtoken.exception.EmailException;
import com.wultra.demo.mtoken.facade.UserFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.UUID;

@RestController
@Tag(name = "Users")
public class UserController {
    private final UserFacade userFacade;

    public UserController(UserFacade userFacade) {
        this.userFacade = userFacade;
    }

    @PostMapping(path = "/registration", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Register a new user",
            description = "Registers the user and sends a verification link to the user's email.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "The user has been registered. activationFingerprint and activationQrCodeData are not present in the response."),
                    @ApiResponse(responseCode = "500", description = "An unexpected server condition has been encountered.", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<RegistrationDto> register(@RequestBody NewUserDto newUser) throws EmailException, IOException {
        RegistrationDto registration = userFacade.register(newUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }

    @PostMapping(path = "/resend-email-verification", consumes = "application/json", produces = "application/json")
    @Operation(
            summary = "Send a new verification link to a registered user.",
            description = "Generates a new verification code and sends it to the user's email.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "The email has been sent. activationFingerprint and activationQrCodeData are not present in the response."),
                    @ApiResponse(responseCode = "404", description = "No user with the given email address has been registered.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "An unexpected server condition has been encountered.", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<RegistrationDto> resendEmailVerification(@RequestBody EmailDto email) throws EmailException, IOException {
        RegistrationDto registration = userFacade.resendEmailVerification(email.getEmail());
        if (registration == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(registration);
    }

    @GetMapping(path = "/email-confirmation", produces = "application/json")
    @Operation(
            summary = "Verify the email address of a registered user.",
            description = "Confirms the user's email address and invalidates the verification code. Render the activationQrCodeData as a QR code and let user to scan it in order to activate the Mobile Token.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "The email address has been verified. activationQrCodeData is present in the response. activationFingerprint is not present in the response."),
                    @ApiResponse(responseCode = "404", description = "No user with the given verification code has been found.", content = @Content),
                    @ApiResponse(responseCode = "500", description = "An unexpected server condition has been encountered.", content = @Content(mediaType = "application/json"))
            }
    )
    public ResponseEntity<RegistrationDto> confirmEmail(
            @RequestParam @Schema(description = "The user's verification code.", example = "f81d4fae-7dec-11d0-a765-00a0c91e6bf6") UUID code
    ) {
        RegistrationDto registration = userFacade.confirmEmail(code);
        if (registration == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(registration);
    }
}
