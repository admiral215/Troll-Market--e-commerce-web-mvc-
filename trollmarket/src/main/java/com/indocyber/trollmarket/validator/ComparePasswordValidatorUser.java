package com.indocyber.trollmarket.validator;

import com.indocyber.trollmarket.dtos.AuthRegisterDto;
import com.indocyber.trollmarket.dtos.admin.AdminRegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ComparePasswordValidatorUser implements ConstraintValidator<ComparePasswordUser, AuthRegisterDto> {

    @Override
    public boolean isValid(AuthRegisterDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}
