package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class GameDTO {
    
    @NotBlank(message = "field name is mandatory")
    private String name;
    
    private String image;
    
    @NotNull(message = "field stockTotal is mandatory")
    @Positive(message = "field stockTotal must be positive")
    private int stockTotal;
    
    @Positive(message = "field pricePerDay must be positive")
    @NotNull(message = "field pricePerDay is mandatory")
    private int pricePerDay;
}
