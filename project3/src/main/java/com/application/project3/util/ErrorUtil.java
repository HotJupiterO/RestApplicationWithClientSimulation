package com.application.project3.util;

import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import java.util.List;

public class ErrorUtil {
    public static void errorToUser(BindingResult bindingResult) {
        StringBuilder errorMessage = new StringBuilder();
        List<FieldError> fieldErrors = bindingResult.getFieldErrors();

        fieldErrors.forEach(s -> errorMessage.append(s.getField())
                .append(" - ").append(s.getDefaultMessage())
                .append(";"));
        throw new WorkingException(errorMessage.toString());
    }
}
