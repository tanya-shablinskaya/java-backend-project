package com.gmail.puhovashablinskaya.service.validators;

import com.gmail.puhovashablinskaya.controller.security.model.AuthRequest;
import com.gmail.puhovashablinskaya.service.config.AuthRequestValidatorConfig;
import com.gmail.puhovashablinskaya.service.config.FieldsConstants;
import com.gmail.puhovashablinskaya.service.config.MessagesConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
@Slf4j
public class AuthRequestValidator implements ConstraintValidator<ValidAuthRequest, AuthRequest> {
    private final AuthRequestValidatorConfig authRequestConfig;

    @Override
    public boolean isValid(AuthRequest authRequest, ConstraintValidatorContext ctx) {
        String username = authRequest.getUsername();
        if (username == null
                || username.isBlank()
        ) {
            String message = MessagesConstants.INVALID_LOGIN_DATA;
            String field = FieldsConstants.USERNAME_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }
        if (username.contains("@")) {
            if (!username.matches(authRequestConfig.getRegexFormatMail())
                    || username.length() > authRequestConfig.getMailMaxLength()
            ) {
                String message = MessagesConstants.BLANK_USERNAME_ERROR;
                String field = FieldsConstants.USERNAME_FIELD_NAME;
                editContext(ctx, message, field);
                return false;
            }
        } else {
            if (username.length() > authRequestConfig.getLoginNameMaxLength()
                    || username.length() < authRequestConfig.getLoginNameMinLength()
                    || !username.matches(authRequestConfig.getRegexLoginNameLatineChar())
                    || !username.matches(authRequestConfig.getRegexLoginNameCharLowerCase())
                    || username.matches(authRequestConfig.getRegexLoginNameDigits())
            ) {
                String message = MessagesConstants.INVALID_LOGIN_DATA;
                String field = FieldsConstants.USERNAME_FIELD_NAME;
                editContext(ctx, message, field);
                return false;
            }
        }

        String password = authRequest.getPassword();
        if (password == null
                || password.isBlank()
                || password.length() > authRequestConfig.getPasswordMaxLength()
                || password.length() < authRequestConfig.getPasswordMinLength()
        ) {
            String message = MessagesConstants.INVALID_LOGIN_DATA;
            String field = FieldsConstants.PASSWORD_FIELD_NAME;
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
