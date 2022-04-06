package com.gmail.puhovashablinskaya.service.model;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
@ToString
@Getter
@EqualsAndHashCode
public class EmployeeDetailsDTO {
    private final String username;
    private LocalDateTime dateOfAuth;
    private final LocalDateTime dateOfLogout;
}
