package com.boardcamp.api.models;

import java.time.LocalDate;
import java.time.ZoneId;

import com.boardcamp.api.dtos.RentalDTO;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "rentals")
public class RentalModel {
    
    public RentalModel(RentalDTO dto, CustomerModel customer, GameModel game, int originalPrice){
        this.customer = customer;
        this.game = game;
        this.daysRented = dto.getDaysRented();
        
        this.rentDate = LocalDate.now(ZoneId.of("GMT-03:00"));
        this.returnDate = null;
        this.originalPrice = originalPrice;
        this.delayFee = 0;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    @Column(nullable = false)
    private LocalDate rentDate;

    @Column(nullable = false)
    private int daysRented;

    @Column()
    private LocalDate returnDate;

    @Column(nullable = false)
    private int originalPrice;

    @Column(nullable = false)
    private int delayFee;

    @OneToOne
    @JoinColumn(name = "customerId")
    private CustomerModel customer;

    @OneToOne
    @JoinColumn(name = "gameId")
    private GameModel game;
}
