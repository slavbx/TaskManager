package org.slavbx.taskmanager.annotation;

import jakarta.validation.Constraint;
import org.slavbx.taskmanager.dto.EnumValidator;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

//Не используется

@Constraint(validatedBy = EnumValidator.class)
@Target({ ElementType.FIELD, ElementType.METHOD, ElementType.PARAMETER, ElementType.ANNOTATION_TYPE })
@Retention(RetentionPolicy.RUNTIME)
public @interface ValidEnum {

    String message() default "Некорректное значение";
    Class<? extends Enum<?>> enumClass();
//    Class<?>[] groups() default {};//
//    Class<? extends Payload>[] payload() default {};
}
