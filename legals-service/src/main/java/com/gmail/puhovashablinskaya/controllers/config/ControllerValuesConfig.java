package com.gmail.puhovashablinskaya.controllers.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ControllerValuesConfig {
    @Value("${page.elements.default.count}")
    private Integer defaultCountOfElements;

    @Value("${legal.iban.regex}")
    private String ibanBynRegex;

    @Value("${legal.unp.regex}")
    private String unpRegex;

    @Value("${legal.employee.count.min}")
    private Integer minCountEmployees;

    @Value("${legal.employee.count.max}")
    private Integer maxCountEmployees;

    @Value("${search.unp.min.length}")
    private Integer minUnpSearchLength;

    @Value("${search.name.min.length}")
    private Integer minNameSearchLength;

    @Value("${search.iban.min.length}")
    private Integer minIbanSearchLength;
}
