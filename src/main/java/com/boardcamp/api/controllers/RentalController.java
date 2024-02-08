package com.boardcamp.api.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.services.RentalService;

import jakarta.validation.Valid;


@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/rentals")
public class RentalController {
    
    final RentalService rentalService;

    RentalController(RentalService rentalService){
        this.rentalService = rentalService;
    }

    @PostMapping
    public ResponseEntity<RentalModel> createRental(@RequestBody @Valid RentalDTO body) {
        RentalModel rental = rentalService.create(body);
        return ResponseEntity.status(HttpStatus.CREATED).body(rental);
    }
    
}
