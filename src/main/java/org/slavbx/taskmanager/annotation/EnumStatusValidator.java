package org.slavbx.taskmanager.annotation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.slavbx.taskmanager.model.Status;

import java.util.Arrays;

public class EnumStatusValidator implements ConstraintValidator<ValidEnumStatus, Status> {
    private Status[] subset;

    @Override
    public void initialize(ValidEnumStatus constraint) {
        this.subset = constraint.anyOf();
    }

    @Override
    public boolean isValid(Status value, ConstraintValidatorContext context) {
        return value == null || Arrays.asList(subset).contains(value);
    }
}