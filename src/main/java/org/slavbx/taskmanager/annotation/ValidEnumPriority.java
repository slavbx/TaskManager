package org.slavbx.taskmanager.annotation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import org.slavbx.taskmanager.model.Priority;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Не используется

@Constraint(validatedBy = EnumPriorityValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnumPriority {

    String message() default "Not correct enum Priority value";
    Priority[] anyOf() default {Priority.MEDIUM, Priority.LOW, Priority.HIGH};
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}
