package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerDTO {
    @NotBlank(message = "field name is mandatory")
    private String name;

    @NotBlank(message = "field cpf is mandatory")
    @Pattern(regexp = "\\d{11}", message = "field cpf must contain 11 digits")
    private String cpf;
}
