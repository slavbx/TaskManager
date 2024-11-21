package org.slavbx.taskmanager.dto;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slavbx.taskmanager.annotation.ValidEnum;
import org.slavbx.taskmanager.model.Priority;

//Не используется

public class EnumValidator implements ConstraintValidator<ValidEnum, Priority> {
    private Enum<?>[] enumConstants;

    @Override
    public void initialize(ValidEnum annotation) {
        this.enumConstants = annotation.enumClass().getEnumConstants();
    }

    @Override
    public boolean isValid(Priority value, ConstraintValidatorContext context) {
        if (value == null) {
            return true;
        }
        for (Enum<?> enumConstant : enumConstants) {
            if (enumConstant.equals(value)) {
                return true;
            }
        }
        return false;
    }
}