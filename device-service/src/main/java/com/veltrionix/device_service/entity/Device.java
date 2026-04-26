package com.veltrionix.device_service.entity;

import com.veltrionix.device_service.model.DeviceType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "devices")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Device {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(length = 255)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(length = 50, name = "type")
    private DeviceType type;

    @Column(length = 255)
    private String location;

    @Column(name = "user_id")
    private Long userId;
}