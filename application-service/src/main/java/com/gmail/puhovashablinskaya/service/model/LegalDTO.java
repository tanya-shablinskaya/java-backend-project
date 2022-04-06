package com.gmail.puhovashablinskaya.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder(toBuilder = true)
@ToString
@Getter
@EqualsAndHashCode
public class LegalDTO {
    @JsonProperty("LegalId")
    private Long id;

    @JsonProperty("Name_Legal")
    private String name;

    @JsonProperty("UNP")
    private Integer unp;

    @JsonProperty("IBANbyBYN")
    private String iban;

    @JsonProperty("Type_legal")
    private String residence;

    @JsonProperty("Total_Employees")
    private Integer countOfEmployees;
}
