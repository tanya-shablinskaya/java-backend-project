package com.gmail.puhovashablinskaya.service.validators;

import com.gmail.puhovashablinskaya.controllers.util.config.ValuesConfig;
import com.gmail.puhovashablinskaya.service.config.FieldsConstants;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.model.EmployeeAddDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

@AllArgsConstructor
@Slf4j
public class EmployeeValidator implements ConstraintValidator<ValidEmployee, EmployeeAddDTO> {
    private final ValuesConfig valuesConfig;

    @Override
    public boolean isValid(EmployeeAddDTO employeeAddDTO, ConstraintValidatorContext ctx) {

        String name = employeeAddDTO.getName();
        if (name == null
                || name.isBlank()
                || name.length() > valuesConfig.getMaxLengthEmployeeName()
                || name.matches(valuesConfig.getEmployeeNamePattern())
        ) {
            String message = MessageConstants.INVALID_EMPLOYEE_DATA_MESSAGE;
            String field = FieldsConstants.EMPLOYEE_NAME_FIELD;
            editContext(ctx, message, field);
            return false;
        }

        LocalDate today = LocalDate.now();
        LocalDate recruitmentDate = employeeAddDTO.getRecruitmentDate();
        if (recruitmentDate == null || recruitmentDate.isAfter(today)) {
            String message = MessageConstants.INVALID_EMPLOYEE_DATA_MESSAGE;
            String field = FieldsConstants.EMPLOYEE_RECRUITMENT_DATE_FIELD;
            editContext(ctx, message, field);
            return false;
        }

        LocalDate terminationDate = employeeAddDTO.getTerminationDate();
        if (terminationDate == null
                || terminationDate.isBefore(today)
                || terminationDate.isBefore(recruitmentDate)
        ) {
            String message = MessageConstants.INVALID_EMPLOYEE_DATA_MESSAGE;
            String field = FieldsConstants.EMPLOYEE_TERMINATION_DATE_FIELD;
            editContext(ctx, message, field);
            return false;
        }

        String ibanByn = employeeAddDTO.getIbanByn();
        if (ibanByn == null || !ibanByn.matches(valuesConfig.getIbanPattern())) {
            String message = MessageConstants.INVALID_EMPLOYEE_DATA_MESSAGE;
            String field = FieldsConstants.EMPLOYEE_IBAN_BYN_FIELD;
            editContext(ctx, message, field);
            return false;
        }

        String ibanCurrency = employeeAddDTO.getIbanCurrency();
        if (ibanCurrency != null && !ibanCurrency.matches(valuesConfig.getIbanPattern())) {
            String message = MessageConstants.INVALID_EMPLOYEE_DATA_MESSAGE;
            String field = FieldsConstants.EMPLOYEE_IBAN_CURRENCY_FIELD;
            editContext(ctx, message, field);
            return false;
        }
        return true;
    }

    private void editContext(ConstraintValidatorContext ctx, String message, String field) {
        ctx.disableDefaultConstraintViolation();
        ctx.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}
