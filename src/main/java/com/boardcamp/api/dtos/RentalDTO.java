package com.boardcamp.api.dtos;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalDTO {
    @NotNull(message = "field customerId is required")
    private Long customerId;

    @NotNull(message = "field gameId is required")
    private Long gameId;

    @NotNull(message = "field daysRented is required")
    @Positive(message = "field daysRented must be greater than zero")
    private int daysRented;
}
