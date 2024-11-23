package org.slavbx.taskmanager.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slavbx.taskmanager.model.Priority;

import java.util.Arrays;

public class EnumPriorityValidator implements ConstraintValidator<ValidEnumPriority, Priority> {
    private Priority[] subset;

    @Override
    public void initialize(ValidEnumPriority constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Priority value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}