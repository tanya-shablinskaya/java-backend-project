package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.service.UserService;
import com.gmail.puhovashablinskaya.service.model.UserDTO;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDTO user = userService.findUserByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("User was not found with username: " + username);
        }
        return UserDetailsImpl.build(user);
    }
}