package com.veltrionix.ingession_service.service;

import com.veltrionix.ingession_service.dto.EnergyUsageDto;
import com.veltrionix.ingession_service.kafka.event.EnergyUsageEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class IngessionService {

    private final KafkaTemplate<String, EnergyUsageEvent> kafkaTemplate;

    public void ingessionEnergyUsage(EnergyUsageDto dto){
        EnergyUsageEvent energyUsageEvent = EnergyUsageEvent.builder()
                .deviceId(dto.deviceId())
                .energyConsumed(dto.energyConsumed())
                .timestamp(dto.timestamp()).build();

        kafkaTemplate.send("energy-usage", energyUsageEvent);
    }
}
