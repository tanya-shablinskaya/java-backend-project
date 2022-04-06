package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.controller.security.model.AuthRequest;
import com.gmail.puhovashablinskaya.repository.UserRepository;
import com.gmail.puhovashablinskaya.repository.model.Employee;
import com.gmail.puhovashablinskaya.repository.model.StatusEmployeeEnum;
import com.gmail.puhovashablinskaya.service.DataTimeService;
import com.gmail.puhovashablinskaya.service.UserService;
import com.gmail.puhovashablinskaya.service.convert.ConvertUser;
import com.gmail.puhovashablinskaya.service.exception.ServiceExeption;
import com.gmail.puhovashablinskaya.service.model.StatusEmployeeEnumDTO;
import com.gmail.puhovashablinskaya.service.model.UserAddDTO;
import com.gmail.puhovashablinskaya.service.model.UserDTO;
import com.gmail.puhovashablinskaya.service.model.UserResponseDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@AllArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final ConvertUser convertUser;
    private final DataTimeService dataTimeService;

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.READ_COMMITTED)
    public UserResponseDTO add(UserAddDTO userDTO) {
        userDTO = userDTO.toBuilder()
                .status(StatusEmployeeEnumDTO.ACTIVE)
                .dateOfCreate(dataTimeService.currentTimeDate())
                .build();
        Employee user = convertUser.convertToUser(userDTO);
        userRepository.add(user);
        return convertUser.convertToResponseDTO(user);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public UserDTO findUserByUsermailOrUsername(String login) {
        return userRepository.findUserByUsermailOrUsername(login)
                .map(convertUser::convertToDTO)
                .orElseThrow(() -> new ServiceExeption("There is no such user with this usermail or username"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public UserDTO findUserByUsermail(String login) {
        return userRepository.findUserByUsermail(login)
                .map(convertUser::convertToDTO)
                .orElseThrow(() -> new ServiceExeption("There is no such user with this usermail"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public UserDTO findUserByUsername(String username) {
        return userRepository.findByUsername(username)
                .map(convertUser::convertToDTO)
                .orElseThrow(() -> new ServiceExeption("There is no such user with this username"));
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean isValidUser(AuthRequest loginDTO) {
        String login = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        UserDTO user = findUserByUsermailOrUsername(login);
        if (user != null) {
            return passwordEncoder.matches(password, user.getPassword());
        } else {
            return false;
        }
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean isActiveUser(String username) {
        Optional<Employee> optionalEmployee = userRepository.findByUsername(username);
        if (optionalEmployee.isPresent()) {
            String statusEmployee = optionalEmployee.get().getStatus().getName().name();
            if (statusEmployee.equals(StatusEmployeeEnum.ACTIVE.name())) {
                return true;
            }
        }
        return false;
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean isUsernameUnique(String username) {
        return userRepository.isUsernameUnique(username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public boolean isUsermailUnique(String usermail) {
        return userRepository.isUsermailUnique(usermail);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void increaseFailedAttempts(UserDTO user) {
        int newFailAttempts = user.getFailedAttempt() + 1;
        String username = user.getUsername();
        userRepository.updateFailedAttempts(newFailAttempts, username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void resetFailedAttempts(String username) {
        userRepository.updateFailedAttempts(0, username);
    }

    @Override
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT)
    public void lock(UserDTO userDTO) {
        Employee employee = convertUser.convertToUser(userDTO);
        Optional<Employee> employeeData = userRepository.findByUsername(employee.getUsername());
        if (employeeData.isPresent()) {
            employee = employeeData.get();
        }
        employee.setLockedAccount(true);
        userRepository.update(employee);
    }
}
