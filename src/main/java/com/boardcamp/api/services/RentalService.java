package com.boardcamp.api.services;

import java.util.List;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.exceptions.GameNotFoundException;
import com.boardcamp.api.exceptions.GameUnprocessableEntityException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@Service
public class RentalService {

    final RentalRepository rentalRepository;
    final CustomerRepository customerRepository;
    final GameRepository gameRepository;

    RentalService(RentalRepository rentalRepository, CustomerRepository customerRepository, GameRepository gameRepository){
        this.rentalRepository = rentalRepository;
        this.customerRepository = customerRepository;
        this.gameRepository = gameRepository;
    }

    public RentalModel create(RentalDTO dto){
        CustomerModel customer = customerRepository.findById(dto.getCustomerId()).orElseThrow(
            () -> new CustomerNotFoundException("Customer not found"));
        GameModel game = gameRepository.findById(dto.getGameId()).orElseThrow(
            () -> new GameNotFoundException("Game not found"));
        int price = game.getPricePerDay() * dto.getDaysRented();

        if(rentalRepository.countByGameId(dto.getGameId()) == game.getStockTotal()){
            throw new GameUnprocessableEntityException("Game out of stock");
        }
        
        RentalModel rental = new RentalModel(dto, customer, game, price);
        return rentalRepository.save(rental);
    }

    public List<RentalModel> findAll(){
        return rentalRepository.findAll();
    }
    
}
