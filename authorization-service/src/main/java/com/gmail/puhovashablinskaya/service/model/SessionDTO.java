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
public class SessionDTO {
    private final String sessionId;
    private final String username;
    private final LocalDateTime dateOfStart;
    private final LocalDateTime dateOfFinish;
    private final StatusSessionEnumDTO statusSession;
}
