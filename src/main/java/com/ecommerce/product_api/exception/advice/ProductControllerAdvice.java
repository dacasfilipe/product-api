package com.ecommerce.product_api.exception.advice;

import com.ecommerce.shopping_client.dto.ErrorDTO;
import com.ecommerce.shopping_client.exception.CategoryNotFoundException;
import com.ecommerce.shopping_client.exception.ProductNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.LocalDateTime;
import java.util.stream.Collectors;

@RestControllerAdvice(basePackages = "com.ecommerce.product-api.controller")
public class ProductControllerAdvice {
    @ExceptionHandler(ProductNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleProductNotFound(ProductNotFoundException ex) {
        return new ErrorDTO(404, ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(CategoryNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorDTO handleCategoryNotFound(CategoryNotFoundException ex) {
        return new ErrorDTO(404, ex.getMessage(), LocalDateTime.now());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorDTO handleValidation(MethodArgumentNotValidException ex) {
        String fields = ex.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField())
                .distinct()
                .collect(Collectors.joining(", "));
        return new ErrorDTO(400, "Valor inválido para o(s) campo(s): "
                + fields, LocalDateTime.now());
    }
}
