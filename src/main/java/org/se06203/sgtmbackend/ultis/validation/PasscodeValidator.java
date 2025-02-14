package org.se06203.sgtmbackend.ultis.validation;


import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;

public class PasscodeValidator implements ConstraintValidator<ValidPasscode, String> {
    @Override
    public void initialize(ValidPasscode constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String passcode, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(passcode))
            return false;
        return passcode.matches("\\d{6}");
    }
}
