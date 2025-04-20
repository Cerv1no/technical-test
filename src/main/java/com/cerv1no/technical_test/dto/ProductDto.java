package com.cerv1no.technical_test.dto;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record ProductDto(
        @NotNull(message = "Product id is required")
        Long id,
        @NotBlank(message = "Product description is required")
        @Pattern(regexp = "^[a-zA-Z0-9 ]*$", message = "Description must be alphanumeric")
        String description,
        @NotNull(message = "Product amount is required")
        @Positive(message = "Amount must be positive")
        int amount
) {
}
