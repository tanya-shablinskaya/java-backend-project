package com.gmail.puhovashablinskaya.service.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LogoutRequestValidator.class)
public @interface ValidLogoutRequest {
    String message() default "Username is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
