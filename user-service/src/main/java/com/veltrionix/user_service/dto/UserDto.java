package com.veltrionix.user_service.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.PositiveOrZero;

public record UserDto(
    Long id,
    String name,
    String surname,

    @Email
    String email,
    String address,
    boolean alerting,

    @PositiveOrZero
    double energyAlertingThreshold
) { }
