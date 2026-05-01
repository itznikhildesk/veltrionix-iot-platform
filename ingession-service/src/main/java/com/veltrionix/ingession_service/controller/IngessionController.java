package com.veltrionix.ingession_service.controller;

import com.veltrionix.ingession_service.dto.EnergyUsageDto;
import com.veltrionix.ingession_service.service.IngessionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/ingession")
@RequiredArgsConstructor
public class IngessionController {

    private final IngessionService ingessionService;

    @PostMapping
    void ingestData(@RequestBody EnergyUsageDto request){
        ingessionService.ingessionEnergyUsage(request);
    }
}
