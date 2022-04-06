package com.gmail.puhovashablinskaya.service.convert.impl;

import com.gmail.puhovashablinskaya.repository.StatusRepository;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.repository.model.Status;
import com.gmail.puhovashablinskaya.repository.model.StatusEmployeeEnum;
import com.gmail.puhovashablinskaya.service.convert.ConvertUser;
import com.gmail.puhovashablinskaya.service.model.StatusEmployeeEnumDTO;
import com.gmail.puhovashablinskaya.service.model.UserAddDTO;
import com.gmail.puhovashablinskaya.service.model.UserDTO;
import com.gmail.puhovashablinskaya.service.model.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@AllArgsConstructor
@Slf4j
public class ConvertUserImpl implements ConvertUser {
    private final PasswordEncoder passwordEncoder;
    private final StatusRepository statusRepository;

    @Override
    public Employee convertToUser(UserAddDTO userAddDTO) {
        String encodePassword = passwordEncoder.encode(userAddDTO.getPassword());
        Employee user = new Employee();
        user.setUsername(userAddDTO.getUsername());
        user.setPassword(encodePassword);
        user.setUsermail(userAddDTO.getUsermail());
        user.setFirstName(userAddDTO.getFirstName());
        user.setDate(userAddDTO.getDateOfCreate());
        user.setFailedAttempt(userAddDTO.getFailedAttempt());
        user.setLockedAccount(userAddDTO.isLockedAccount());
        Optional<Status> statusEmployee = findStatus(userAddDTO.getStatus());
        statusEmployee.ifPresent(user::setStatus);
        return user;
    }

    @Override
    public Employee convertToUser(UserDTO userDTO) {
        String encodePassword = passwordEncoder.encode(userDTO.getPassword());
        Employee user = new Employee();
        user.setUsername(userDTO.getUsername());
        user.setPassword(encodePassword);
        user.setUsermail(userDTO.getUsermail());
        user.setFirstName(userDTO.getFirstName());
        user.setDate(userDTO.getDateOfCreate());
        Optional<Status> status = findStatus(userDTO.getStatus());
        status.ifPresent(user::setStatus);
        user.setLockedAccount(userDTO.isLockedAccount());
        user.setFailedAttempt(userDTO.getFailedAttempt());
        return user;
    }


    @Override
    public UserDTO convertToDTO(Employee user) {
        return UserDTO.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .usermail(user.getUsermail())
                .firstName(user.getFirstName())
                .status(findStatusDTO(user.getStatus()))
                .dateOfCreate(user.getDate())
                .lockedAccount(user.isLockedAccount())
                .failedAttempt(user.getFailedAttempt())
                .build();
    }

    @Override
    public UserResponseDTO convertToResponseDTO(Employee user) {
        return UserResponseDTO.builder()
                .userId(user.getId())
                .status(findStatusDTO(user.getStatus()))
                .build();
    }

    private Optional<Status> findStatus(StatusEmployeeEnumDTO status) {
        String statusString = status.name();
        StatusEmployeeEnum statusEmployeeEnum = StatusEmployeeEnum.valueOf(statusString);
        return statusRepository.findByName(statusEmployeeEnum);
    }

    private StatusEmployeeEnumDTO findStatusDTO(Status status) {
        String statusString = status.getName().name();
        return StatusEmployeeEnumDTO.valueOf(statusString);
    }
}

