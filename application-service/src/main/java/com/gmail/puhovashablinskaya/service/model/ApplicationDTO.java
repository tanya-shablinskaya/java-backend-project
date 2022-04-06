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
public class ApplicationDTO {

    @JsonProperty("Application Id")
    private String applicationId;

    @JsonProperty("Value_Leg")
    private CurrencyEnumDTO currencyFrom;

    @JsonProperty("Value_Ind")
    private CurrencyEnumDTO currencyTo;

    @JsonProperty("Employee Id")
    private Long employeeId;

    @JsonProperty("Full_Name_Individual")
    private String employeeName;

    @JsonProperty("Status")
    private StatusApplicationEnumDTO status;

    @JsonProperty("Percent_Conv")
    private Float percent;

    @JsonProperty("Name_Legal")
    private String legalName;
}
