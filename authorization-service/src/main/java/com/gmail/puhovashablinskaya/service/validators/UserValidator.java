package com.gmail.puhovashablinskaya.service.validators;

import com.gmail.puhovashablinskaya.service.UserService;
import com.gmail.puhovashablinskaya.service.config.AuthRequestValidatorConfig;
import com.gmail.puhovashablinskaya.service.config.FieldsConstants;
import com.gmail.puhovashablinskaya.service.config.MessagesConstants;
import com.gmail.puhovashablinskaya.service.config.ServiceConfig;
import com.gmail.puhovashablinskaya.service.model.UserAddDTO;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
@Slf4j
public class UserValidator implements ConstraintValidator<ValidUser, UserAddDTO> {
    private final AuthRequestValidatorConfig authRequestConfig;
    private final ServiceConfig serviceConfig;
    private final UserService userService;

    @Override
    public boolean isValid(UserAddDTO userAddDTO, ConstraintValidatorContext ctx) {
        String username = userAddDTO.getUsername();
        if (username == null || username.isBlank()) {
            String message = MessagesConstants.USERNAME_NULL_MESSAGE;
            String field = FieldsConstants.USERNAME_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }
        if (username.length() > authRequestConfig.getLoginNameMaxLength() ||
                username.length() < authRequestConfig.getLoginNameMinLength()) {
            String message = MessagesConstants.USERNAME_LENGTH_ERROR;
            String field = FieldsConstants.USERNAME_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }
        if (!username.matches(authRequestConfig.getRegexLoginNameCharLowerCase())) {
            String message = MessagesConstants.USERNAME_FORMAT_ERROR;
            String field = FieldsConstants.USERNAME_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }
        if (!userService.isUsernameUnique(username)) {
            String message = MessagesConstants.USERNAME_IS_NOT_UNIQUE;
            String field = FieldsConstants.USERNAME_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }

        String password = userAddDTO.getPassword();
        if (password == null || password.isBlank()) {
            String message = MessagesConstants.PASSWORD_NULL_ERROR;
            String field = FieldsConstants.USERNAME_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }

        if (password.length() > authRequestConfig.getPasswordMaxLength()
                || password.length() < authRequestConfig.getPasswordMinLength()
        ) {
            String message = MessagesConstants.PASSWORD_LENGTH_ERROR;
            String field = FieldsConstants.PASSWORD_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }

        String usermail = userAddDTO.getUsermail();
        if (usermail == null || usermail.isBlank()) {
            String message = MessagesConstants.EMAIL_EMPTY_ERROR;
            String field = FieldsConstants.PASSWORD_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }
        if (!usermail.matches(authRequestConfig.getRegexFormatMail())) {
            String message = MessagesConstants.EMAIL_FORMAT_ERROR;
            String field = FieldsConstants.USERMAIL_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }
        if (usermail.length() > authRequestConfig.getMailMaxLength()) {
            String message = MessagesConstants.EMAIL_LENGTH_ERROR;
            String field = FieldsConstants.USERMAIL_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }

        if (!userService.isUsermailUnique(usermail)) {
            String message = MessagesConstants.EMAIL_NOT_UNIQUE_ERROR;
            String field = FieldsConstants.USERMAIL_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }

        String firstName = userAddDTO.getFirstName();
        if (firstName == null || firstName.isBlank()) {
            String message = MessagesConstants.USER_FIRSTNAME_NULL_ERROR;
            String field = FieldsConstants.FIRSTNAME_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }
        if (!firstName.matches(authRequestConfig.getRegexFirstNameRusChar())) {
            String message = MessagesConstants.USER_FIRSTNAME_LANGUAGE_ERROR;
            String field = FieldsConstants.FIRSTNAME_FIELD_NAME;
            editContext(ctx, message, field);
            return false;
        }
        if (firstName.length() > serviceConfig.getMaxLengthFirstName()) {
            String message = MessagesConstants.USER_FIRSTNAME_LENGTH_ERROR;
            String field = FieldsConstants.FIRSTNAME_FIELD_NAME;
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
