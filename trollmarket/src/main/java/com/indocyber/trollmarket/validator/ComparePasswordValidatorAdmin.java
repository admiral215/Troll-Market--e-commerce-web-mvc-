package com.indocyber.trollmarket.validator;

import com.indocyber.trollmarket.dtos.admin.AdminRegisterDto;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ComparePasswordValidatorAdmin implements ConstraintValidator<ComparePasswordAdmin, AdminRegisterDto> {

    @Override
    public boolean isValid(AdminRegisterDto dto, ConstraintValidatorContext constraintValidatorContext) {
        return dto.getPassword().equals(dto.getConfirmPassword());
    }
}
