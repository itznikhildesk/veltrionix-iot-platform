package com.veltrionix.device_service.service;

import com.veltrionix.device_service.dto.DeviceDto;
import com.veltrionix.device_service.entity.Device;
import com.veltrionix.device_service.mapper.DeviceMapper;
import com.veltrionix.device_service.repository.DeviceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class DeviceService {

    private final DeviceRepository deviceRepository;
    private final DeviceMapper deviceMapper;

    public DeviceDto getDeviceById(Long id){
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("No device found"));
        return deviceMapper.toDto(device);
    }

    public DeviceDto createDevice(DeviceDto request){
        Device device = deviceRepository.save(deviceMapper.toEntity(request));
        return deviceMapper.toDto(device);
    }

    public DeviceDto updateDevice(DeviceDto request, Long id){
        return deviceRepository.findById(id)
                .map(device -> {
                    device.setName(request.name());
                    device.setType(request.type());
                    device.setLocation(request.location());
                    device.setUserId(request.userId());

                    Device updatedDevice = deviceRepository.save(device);
                    return deviceMapper.toDto(updatedDevice);
                }).orElse(null);

    }

    public void deleteDevice(Long id){
        if(!deviceRepository.existsById(id)){
            throw new IllegalArgumentException("Device not found");
        }
        deviceRepository.deleteById(id);
    }

    public List<DeviceDto> getAllDevices(){
        return deviceRepository.findAll()
                .stream()
                .map(deviceMapper::toDto)
                .toList();
    }

}
