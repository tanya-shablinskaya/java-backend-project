package com.gmail.puhovashablinskaya.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.gmail.puhovashablinskaya.service.validators.ValidEmployee;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;

@Builder(toBuilder = true)
@ToString
@Getter
@EqualsAndHashCode
@ValidEmployee
public class EmployeeAddDTO {
    @JsonProperty("Full_Name_Individual")
    private String name;

    @JsonProperty("Recruitment_date")
    private LocalDate recruitmentDate;

    @JsonProperty("Termination_date")
    private LocalDate terminationDate;

    @JsonProperty("Name_Legal")
    private String legalName;

    @JsonProperty("Person_Iban_Byn")
    private String ibanByn;

    @JsonProperty("Person_Iban_Currency")
    private String ibanCurrency;
}
