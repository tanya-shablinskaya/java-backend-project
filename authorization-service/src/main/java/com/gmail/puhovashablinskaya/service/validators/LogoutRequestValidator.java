package com.gmail.puhovashablinskaya.service.validators;

import com.gmail.puhovashablinskaya.controller.security.model.LogoutRequest;
import com.gmail.puhovashablinskaya.service.config.AuthRequestValidatorConfig;
import com.gmail.puhovashablinskaya.service.config.FieldsConstants;
import com.gmail.puhovashablinskaya.service.config.MessagesConstants;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
@Slf4j
public class LogoutRequestValidator implements ConstraintValidator<ValidLogoutRequest, LogoutRequest> {
    private final AuthRequestValidatorConfig authRequestConfig;

    @Override
    public boolean isValid(LogoutRequest logoutRequest, ConstraintValidatorContext ctx) {
        String username = logoutRequest.getUsername();
        if (username.length() > authRequestConfig.getLoginNameMaxLength()
                || username.length() < authRequestConfig.getLoginNameMinLength()
                || !username.matches(authRequestConfig.getRegexLoginNameLatineChar())
                || !username.matches(authRequestConfig.getRegexLoginNameCharLowerCase())
                || username.matches(authRequestConfig.getRegexLoginNameDigits())
        ) {
            String message = MessagesConstants.INVALID_LOGOUT_DATA;
            String field = FieldsConstants.USERNAME_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }
        String sessionId = logoutRequest.getSessionId();
        if (sessionId.isBlank()) {
            String message = MessagesConstants.INVALID_LOGOUT_DATA;
            String field = FieldsConstants.SESSION_ID_FIELD_NAME;
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
