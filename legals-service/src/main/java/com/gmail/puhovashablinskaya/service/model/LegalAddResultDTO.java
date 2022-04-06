package com.gmail.puhovashablinskaya.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.puhovashablinskaya.controllers.validators.ValidAddLegal;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@ToString
@Getter
@EqualsAndHashCode
@ValidAddLegal
public class LegalAddResultDTO {

    @JsonProperty("Legal")
    private LegalDTO legalDTO;

    private String message;
}
