package com.gmail.puhovashablinskaya.controllers.validators;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.TYPE, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = LegalValidator.class)
public @interface ValidAddLegal {
    String message() default "Legal is not valid";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
