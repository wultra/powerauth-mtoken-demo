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
package com.wultra.demo.mtoken.service;

import com.sendgrid.Method;
import com.sendgrid.Request;
import com.sendgrid.Response;
import com.sendgrid.SendGrid;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.Content;
import com.sendgrid.helpers.mail.objects.Email;
import com.wultra.demo.mtoken.data.dto.UserDto;
import com.wultra.demo.mtoken.exception.EmailException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.MessageFormat;

@Service
public class SendGridEmailService implements IEmailService {
    private final MessageFormat emailVerificationBodyFormat;
    private final String emailVerificationSubject;
    private final Email from;
    private final SendGrid sendGrid;

    public SendGridEmailService(
            SendGrid sendGrid,
            @Value("${email.from.address}") String fromAddress,
            @Value("${email.from.name:#{null}}") String fromName,
            @Value("${email.email-verification.subject}") String emailVerificationSubject,
            @Value("${email.email-verification.body}") String emailVerificationBodyPattern
    ) {
        this.emailVerificationBodyFormat = new MessageFormat(emailVerificationBodyPattern);
        this.emailVerificationSubject = emailVerificationSubject;
        this.sendGrid = sendGrid;

        this.from = new Email(fromAddress);
        if (fromName != null) {
            this.from.setName(fromName);
        }
    }

    @Override
    public void sendEmailVerification(UserDto toUser, String emailVerificationCode) throws EmailException, IOException {
        Email to = new Email(toUser.getEmail(), toUser.getFullName());

        Object[] bodyArgs = {emailVerificationCode};
        String body = emailVerificationBodyFormat.format(bodyArgs);
        Content content = new Content("text/plain", body);

        send(to, emailVerificationSubject, content);
    }

    protected void send(Email to, String subject, Content content) throws EmailException, IOException {
        String body;
        try {
            body = new Mail(from, subject, to, content).build();
        } catch (IOException e) {
            throw new EmailException("The request to send the email cannot be built", e);
        }

        Request request = new Request();
        request.setMethod(Method.POST);
        request.setEndpoint("mail/send");
        request.setBody(body);

        Response response = sendGrid.api(request);

        if (response.getStatusCode() != 202) {
            throw new EmailException("The request to send the email has failed: " + response.getStatusCode() + ": " + response.getBody());
        }
    }
}
