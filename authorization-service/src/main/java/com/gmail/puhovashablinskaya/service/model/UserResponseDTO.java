package com.gmail.puhovashablinskaya.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@ToString
@EqualsAndHashCode
@Builder(toBuilder = true)
@Getter
public class UserResponseDTO {
    @JsonProperty("UserId")
    private Long userId;
    @JsonProperty("Status")
    private StatusEmployeeEnumDTO status;
}
