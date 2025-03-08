package org.se06203.besgtn.utils.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import org.apache.commons.lang3.StringUtils;
import org.se06203.besgtn.config.ErrorCode;
import org.se06203.besgtn.config.exception.ServerException;
import org.se06203.besgtn.utils.Constants;

public class EmailValidator implements ConstraintValidator<ValidEmail, String> {
    @Override
    public void initialize(ValidEmail constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
    }

    @Override
    public boolean isValid(String email, ConstraintValidatorContext constraintValidatorContext) {
        if (StringUtils.isEmpty(email))
            throw new ServerException(ErrorCode.MISSING_PARAMETERS);
        if (!email.matches(Constants.EMAIL_REGEX)) {
            throw new ServerException(ErrorCode.INVALID_EMAIL, "Invalid email format");
        }
        return true;
    }
}
