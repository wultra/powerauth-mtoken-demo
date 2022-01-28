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

import com.wultra.demo.mtoken.data.dao.IDeviceRepository;
import com.wultra.demo.mtoken.data.dto.NewDeviceDto;
import com.wultra.demo.mtoken.data.entity.Device;
import com.wultra.demo.mtoken.data.entity.User;
import com.wultra.demo.mtoken.data.mapper.DeviceMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeviceService {
    private final DeviceMapper deviceMapper;
    private final IDeviceRepository deviceRepository;

    public DeviceService(DeviceMapper deviceMapper, IDeviceRepository deviceRepository) {
        this.deviceMapper = deviceMapper;
        this.deviceRepository = deviceRepository;
    }

    public Device create(User user, NewDeviceDto newDeviceDto) {
        Device device = deviceMapper.toDevice(user, newDeviceDto);
        device.setId(UUID.randomUUID());
        return deviceRepository.save(device);
    }
}
