package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class RentalsIntegrationTests {
    
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private RentalRepository rentalRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    void cleanUpDatabase() {
        rentalRepository.deleteAll();
        customerRepository.deleteAll();
        gameRepository.deleteAll();
    }

    @Test
    void givenWrongCustomer_whenCreatingRental_thenThrowsError(){
        RentalDTO rentalDTO = new RentalDTO(1L, 1L, 1);
        GameModel game = new GameModel(1L, "test", null, 1, 1);
        gameRepository.save(game);
        HttpEntity<RentalDTO> body = new HttpEntity<>(rentalDTO);

        ResponseEntity<String> response = restTemplate.exchange("/rentals", HttpMethod.POST, body, String.class);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertEquals(0, rentalRepository.count());
    }

    @Test
    void givenValidRental_whenCreatingRental_thenCreatesRental() {
        GameModel game = new GameModel(1L,"test", null, 1, 1);
        CustomerModel customer = new CustomerModel("test","test");
        gameRepository.save(game);
        customerRepository.save(customer);
        RentalDTO rentalDTO = new RentalDTO(customer.getId(), game.getId(), 1);
        HttpEntity<RentalDTO> body = new HttpEntity<>(rentalDTO);

        ResponseEntity<String> response = restTemplate.exchange("/rentals", HttpMethod.POST, body, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, rentalRepository.count());
    }
}
