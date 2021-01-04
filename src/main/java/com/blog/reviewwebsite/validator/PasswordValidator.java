package com.blog.reviewwebsite.validator;

import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class PasswordValidator implements ConstraintValidator<MatchingPasswords, Object> {

    private String password;
    private String confirmPassword;

    @Override
    public void initialize(MatchingPasswords constraintAnnotation) {
        this.password = constraintAnnotation.password();
        this.confirmPassword = constraintAnnotation.confirmPassword();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        Object passwordValue = new BeanWrapperImpl(value).getPropertyValue(password);
        Object passwordMatchValue = new BeanWrapperImpl(value).getPropertyValue(confirmPassword);

        //password being not null is validated beforehand; nullPointerException, therefore, won't be produced
        if (!passwordValue.equals(passwordMatchValue)) {
            context.disableDefaultConstraintViolation();
            context.buildConstraintViolationWithTemplate(context.getDefaultConstraintMessageTemplate()).
                    addPropertyNode("confirmPassword").addConstraintViolation();
            return false;
        } else {
            return true;
        }

    }
}
