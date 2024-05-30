package com.indocyber.trollmarket.validator;

import com.indocyber.trollmarket.services.AuthService;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class UsernameValidator implements ConstraintValidator<Username,String> {
    private final AuthService authService;

    public UsernameValidator(AuthService authService) {
        this.authService = authService;
    }

    @Override
    public boolean isValid(String inputUsername, ConstraintValidatorContext constraintValidatorContext) {
        return !authService.isUsernameExist(inputUsername);
    }
}
