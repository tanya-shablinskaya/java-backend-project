package com.gmail.puhovashablinskaya.controllers.validators;

import com.gmail.puhovashablinskaya.controllers.config.ControllerValuesConfig;
import com.gmail.puhovashablinskaya.service.config.MessageConstants;
import com.gmail.puhovashablinskaya.service.model.LegalAddDTO;
import com.gmail.puhovashablinskaya.service.model.ResidenceEnum;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
@Slf4j
public class LegalValidator implements ConstraintValidator<ValidAddLegal, LegalAddDTO> {
    private final ControllerValuesConfig validateValues;

    @Override
    public boolean isValid(LegalAddDTO legalAddDTO, ConstraintValidatorContext ctx) {
        String name = legalAddDTO.getName();
        if (name == null || name.isBlank()) {
            String message = MessageConstants.INVALID_INPUT_ERROR;
            String field = "name";
            editContext(ctx, message, field);
            return false;
        }
        String unp = String.valueOf(legalAddDTO.getUnp());
        if (unp == null || !unp.matches(validateValues.getUnpRegex())) {
            String message = MessageConstants.INVALID_INPUT_ERROR;
            String field = "unp";
            editContext(ctx, message, field);
            return false;
        }
        String iban = legalAddDTO.getIban();
        if (iban == null || !iban.matches(validateValues.getIbanBynRegex())) {
            String message = MessageConstants.INVALID_INPUT_ERROR;
            String field = "iban";
            editContext(ctx, message, field);
            return false;
        }
        ResidenceEnum residence = legalAddDTO.getResidence();
        if (residence == null) {
            String message = MessageConstants.INVALID_INPUT_ERROR;
            String field = "residence";
            editContext(ctx, message, field);
            return false;
        }
        Integer countOfEmployees = legalAddDTO.getCountOfEmployees();
        if (countOfEmployees <= validateValues.getMinCountEmployees()
                || countOfEmployees > validateValues.getMaxCountEmployees()) {
            String message = MessageConstants.INVALID_INPUT_ERROR;
            String field = "countOfEmployees";
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
