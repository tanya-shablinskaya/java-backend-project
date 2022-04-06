package com.gmail.puhovashablinskaya.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Builder(toBuilder = true)
@ToString
@Getter
@EqualsAndHashCode
public class LegalDTO {
    @JsonProperty("LegalId")
    private Long id;

    @NotBlank
    @JsonProperty("Name_Legal")
    private String name;

    @NotNull
    @JsonProperty("UNP")
    private Integer unp;

    @NotNull
    @JsonProperty("IBANbyBYN")
    private String iban;

    @NotNull
    @JsonProperty("Type_legal")
    private ResidenceEnum residence;

    @NotBlank
    @JsonProperty("Total_Employees")
    private Integer countOfEmployees;
}
