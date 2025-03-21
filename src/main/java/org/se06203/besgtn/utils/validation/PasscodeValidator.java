package org.se06203.besgtn.utils.validation;

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
        if (StringUtils.isEmpty(passcode) || passcode.length() < 6)
            return false;

        // Regex kiểm tra mật khẩu: ít nhất 6 ký tự, có chữ hoa, chữ thường, số, ký tự đặc biệt
        return passcode.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{6,}$");
    }
}
