package com.gmail.puhovashablinskaya.service.model;


import com.gmail.puhovashablinskaya.service.validators.ValidUser;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
@ValidUser
public class UserAddDTO {
    private String username;
    private String password;
    private String usermail;
    private String firstName;
    private StatusEmployeeEnumDTO status;
    private LocalDateTime dateOfCreate;
    private final boolean lockedAccount;
    private final int failedAttempt;

}
