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
CREATE TABLE device
(
    id                     UUID         NOT NULL,
    "user"                 UUID         NOT NULL,
    name                   VARCHAR(255) NOT NULL,
    platform               VARCHAR(255) NOT NULL,
    device_info            VARCHAR(255) NOT NULL,
    activation_fingerprint VARCHAR(255) NOT NULL,
    CONSTRAINT pk_device PRIMARY KEY (id)
);

ALTER TABLE device
    ADD CONSTRAINT fk_device_on_user FOREIGN KEY ("user") REFERENCES "user" (id);
