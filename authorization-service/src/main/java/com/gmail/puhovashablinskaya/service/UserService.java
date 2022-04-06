package com.gmail.puhovashablinskaya.service;


import com.gmail.puhovashablinskaya.controller.security.model.AuthRequest;
import com.gmail.puhovashablinskaya.service.model.UserAddDTO;
import com.gmail.puhovashablinskaya.service.model.UserDTO;
import com.gmail.puhovashablinskaya.service.model.UserResponseDTO;

public interface UserService {
    UserResponseDTO add(UserAddDTO userDTO);

    UserDTO findUserByUsermailOrUsername(String login);

    UserDTO findUserByUsermail(String login);

    UserDTO findUserByUsername(String username);

    boolean isValidUser(AuthRequest loginDTO);

    boolean isActiveUser(String username);

    boolean isUsernameUnique(String username);

    boolean isUsermailUnique(String usermail);

    void increaseFailedAttempts(UserDTO user);

    void resetFailedAttempts(String username);

    void lock(UserDTO user);
}
