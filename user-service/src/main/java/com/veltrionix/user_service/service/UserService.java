package com.veltrionix.user_service.service;

import com.veltrionix.user_service.dto.UserDto;
import com.veltrionix.user_service.entity.User;
import com.veltrionix.user_service.maper.UserMapper;
import com.veltrionix.user_service.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserDto createUser(UserDto request){
        log.info("Creating user: {}", request);

        User user = userMapper.toUserEntity(request);
        User savedUser = userRepository.save(user);

        return userMapper.toDto(savedUser);
    }


    public UserDto getUserById(Long id){
        log.info("Getting user by id: {}", id);
        return userRepository.findById(id)
                .map(userMapper::toDto)
                .orElse(null);
    }

    public List<UserDto> getAllUsers(){
        log.info("Fetching all users");
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDto)
                .toList();
    }


    public UserDto updateUser(Long id, UserDto request) {
        return userRepository.findById(id)
                             .map(user -> {
                                 user.setName(request.name());
                                 user.setSurname(request.surname());
                                 user.setEmail(request.email());
                                 user.setAddress(request.address());
                                 user.setAlerting(request.alerting());
                                 user.setEnergyAlertingThreshold(request.energyAlertingThreshold());

                                 User updated = userRepository.save(user);
                                 return userMapper.toDto(updated);
                             })
                             .orElse(null);
    }



}
