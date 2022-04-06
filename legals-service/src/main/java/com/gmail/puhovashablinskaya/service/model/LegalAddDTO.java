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
public class LegalAddDTO {

    @JsonProperty("Name_Legal")
    private String name;

    @JsonProperty("UNP")
    private Integer unp;

    @JsonProperty("IBANbyBYN")
    private String iban;

    @JsonProperty("Type_legal")
    private ResidenceEnum residence;

    @JsonProperty("Total_Employees")
    private Integer countOfEmployees;
}
