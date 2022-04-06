package com.gmail.puhovashablinskaya.controllers.util.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ValuesConfig {
    @Value("${page.employees.default.count}")
    private Integer defaultCountOfElementsPage;

    @Value("${legal.unp.regex}")
    private String regexUnpLegal;

    @Value("${search.min.length}")
    private Integer minLengthSearch;

    @Value("${search.unp.max.length}")
    private Integer maxLengthUnp;

    @Value("${employees.name.max.length}")
    private Integer maxLengthEmployeeName;

    @Value("${search.legal.name.max.length}")
    private Integer maxLengthLegalName;

    @Value("${employee.name.pattern}")
    private String employeeNamePattern;

    @Value("${employee.iban.regex}")
    private String ibanPattern;
}
