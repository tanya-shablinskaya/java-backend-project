package com.gmail.puhovashablinskaya.service.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Builder(toBuilder = true)
@ToString
@Getter
@EqualsAndHashCode
public class EmployeeDTO {
    @JsonProperty("Employee Id")
    private Long id;

    @JsonProperty("Full_Name_Individual")
    private String name;

    @JsonProperty("Recruitment_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate recruitmentDate;

    @JsonProperty("Termination_date")
    @DateTimeFormat(pattern = "dd/MM/yyyy")
    private LocalDate terminationDate;

    @JsonProperty("Name_Legal")
    private String legalName;

    @JsonProperty("Person_Iban_Byn")
    private String ibanByn;

    @JsonProperty("Person_Iban_Currency")
    private String ibanCurrency;
}
