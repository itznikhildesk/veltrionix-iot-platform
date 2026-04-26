package com.veltrionix.user_service.maper;

import com.veltrionix.user_service.dto.UserDto;
import com.veltrionix.user_service.entity.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapper {

    public User toUserEntity(UserDto dto){
        if (dto == null) return null;

        return User.builder()
                   .id(dto.id())
                   .name(dto.name())
                   .surname(dto.surname())
                   .email(dto.email())
                   .address(dto.address())
                   .alerting(dto.alerting())
                   .energyAlertingThreshold(dto.energyAlertingThreshold())
                   .build();
    }

    public UserDto toDto(User entity) {
        if (entity == null) return null;

        return new UserDto(
                entity.getId(),
                entity.getName(),
                entity.getSurname(),
                entity.getEmail(),
                entity.getAddress(),
                entity.isAlerting(),
                entity.getEnergyAlertingThreshold()
        );
    }
}
