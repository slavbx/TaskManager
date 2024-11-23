package org.slavbx.taskmanager.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.slavbx.taskmanager.model.Status;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Не используется

@Constraint(validatedBy = EnumStatusValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnumStatus {

    String message() default "Not correct enum Status value";
    Status[] anyOf() default {Status.WAIT, Status.PROGRESS, Status.COMPLETE};
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
