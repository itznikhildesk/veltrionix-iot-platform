package com.veltrionix.device_service.controller;

import com.veltrionix.device_service.dto.DeviceDto;
import com.veltrionix.device_service.service.DeviceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/devices")
@RequiredArgsConstructor
class DeviceController {

    private final DeviceService deviceService;

    @GetMapping("/{id}")
    ResponseEntity<DeviceDto> getDeviceById(@PathVariable Long id){
        return ResponseEntity.ok(deviceService.getDeviceById(id));
    }

    @PostMapping("/create")
    ResponseEntity<DeviceDto> createDevice(@RequestBody DeviceDto request){
        return ResponseEntity.ok(deviceService.createDevice(request));
    }

    @PutMapping("/{id}")
    ResponseEntity<DeviceDto> updateDevice(@PathVariable Long id, @RequestBody DeviceDto request){
        return ResponseEntity.ok(deviceService.updateDevice(request, id));
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteDevice(Long id){
        deviceService.deleteDevice(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping()
    ResponseEntity<List<DeviceDto>> getAllDevices(){
        return ResponseEntity.ok(deviceService.getAllDevices());
    }




}
