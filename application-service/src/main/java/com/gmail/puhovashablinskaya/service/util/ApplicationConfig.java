package com.gmail.puhovashablinskaya.service.util;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class ApplicationConfig {
    @Value("${count.char.percent}")
    private Integer countOfCharPercent;

    @Value("${count.char.date}")
    private Integer countOfCharDate;

    @Value("${count.char.value.currency}")
    private Integer countOfCharValueCurrency;

    @Value("${max.char.value.employee.id}")
    private Integer maxLengthEmployeeId;

    @Value("${max.length.name.legal}")
    private Integer maxLengthNameLegal;

    @Value("${regex.value.currency}")
    private String regexCurrency;

    @Value("${regex.new.line}")
    private String regexNewLine;

    @Value("${regex.splited}")
    private String regexSplited;

    @Value("${page.employees.default.count}")
    private Integer defaultCountOfElementsPage;
}
