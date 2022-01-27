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
package com.wultra.mtoken.rest.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.Jackson2ObjectMapperBuilder;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.client.RestTemplate;

@Configuration
public class WultraMtokenRestConfiguration {
    @Bean
    public RestTemplate wultraMtokenRestTemplate(
            RestTemplateBuilder restTemplateBuilder,
            @Value("${wultra-mtoken.rest.root-uri}") String rootUri,
            @Value("${wultra-mtoken.rest.username}") String username,
            @Value("${wultra-mtoken.rest.password}") String password) {
        return restTemplateBuilder
                .rootUri(rootUri)
                .basicAuthentication(username, password)
                .defaultHeader(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON.toString())
                .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON.toString())
                .messageConverters(
                        new MappingJackson2HttpMessageConverter(
                                new Jackson2ObjectMapperBuilder()
                                        .featuresToDisable(DeserializationFeature.READ_DATE_TIMESTAMPS_AS_NANOSECONDS)
                                        .build()
                        )
                )
                .build();
    }
}
