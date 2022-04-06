package com.gmail.puhovashablinskaya.service.impl;

import com.gmail.puhovashablinskaya.service.model.UserDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@ToString
@EqualsAndHashCode
@AllArgsConstructor
@Slf4j
public class UserDetailsImpl implements UserDetails {

    private final Long id;
    private final String usermail;
    private final String username;
    private final String password;

    public static UserDetailsImpl build(UserDTO user) {
        return new UserDetailsImpl(
                user.getId(),
                user.getUsermail(),
                user.getUsername(),
                user.getPassword()
        );
    }

    public Long getId() {
        return id;
    }

    public String getUsermail() {
        return usermail;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}

