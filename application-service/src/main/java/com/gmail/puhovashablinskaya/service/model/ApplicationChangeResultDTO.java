package com.gmail.puhovashablinskaya.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@ToString
@Getter
@EqualsAndHashCode
public class ApplicationChangeResultDTO {

    @JsonProperty("LegalId")
    private Long legalId;

    private String message;
}
