package com.gmail.puhovashablinskaya.service.model;

import com.gmail.puhovashablinskaya.service.validators.ValidUser;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@ToString
@EqualsAndHashCode
@Getter
@ValidUser
public class UserDTO {
    private final Long id;
    private final String username;
    private final String password;
    private final String usermail;
    private final String firstName;
    private final StatusEmployeeEnumDTO status;
    private final LocalDateTime dateOfCreate;
    private final boolean lockedAccount;
    private final int failedAttempt;

}
