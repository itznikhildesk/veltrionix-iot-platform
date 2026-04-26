package com.veltrionix.device_service.mapper;

import com.veltrionix.device_service.dto.DeviceDto;
import com.veltrionix.device_service.entity.Device;
import org.springframework.stereotype.Component;

@Component
public class DeviceMapper {
    public Device toEntity(DeviceDto dto) {
        if (dto == null) return null;

        return Device.builder()
                     .id(dto.id())
                     .name(dto.name())
                     .type(dto.type())
                     .location(dto.location())
                     .userId(dto.userId())
                     .build();
    }

    public DeviceDto toDto(Device entity) {
        if (entity == null) return null;

        return new DeviceDto(
                entity.getId(),
                entity.getName(),
                entity.getType(),
                entity.getLocation(),
                entity.getUserId()
        );
    }

}
