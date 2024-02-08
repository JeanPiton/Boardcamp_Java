package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.Optional;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.RentalDTO;
import com.boardcamp.api.exceptions.CustomerNotFoundException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.models.RentalModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.repositories.RentalRepository;
import com.boardcamp.api.services.RentalService;

@SpringBootTest
class RentalsUnitTests {
    
    @InjectMocks
    private RentalService rentalService;

    @Mock
    private RentalRepository rentalRepository;

    @Mock
    private CustomerRepository customerRepository;

    @Mock
    private GameRepository gameRepository;

    @Test
    void givenWrongCustomer_whenCreatingRental_thenThrowsError() {
        RentalDTO rental = new RentalDTO(1L, 1L, 2);
        GameModel game = new GameModel();
        game.setId(1L);
        doReturn(Optional.empty()).when(customerRepository).findById(any());
        doReturn(Optional.of(game)).when(gameRepository).findById(any());

        CustomerNotFoundException exception = assertThrows(CustomerNotFoundException.class, () -> rentalService.create(rental));

        assertNotNull(exception);
        assertEquals("Customer not found", exception.getMessage());
        verify(rentalRepository, times(0)).save(any());
    }

    @Test
    void givenValidRental_whenCreatingRental_thenCreatesRental() {
        RentalDTO rental = new RentalDTO(1L, 1L, 1);
        GameModel game = new GameModel(1L, "test", null, 1, 1);
        CustomerModel customer = new CustomerModel("test", "11111111111");
        customer.setId(1L);
        RentalModel newRental = new RentalModel(rental, customer, game, 1);
        doReturn(Optional.of(customer)).when(customerRepository).findById(any());
        doReturn(Optional.of(game)).when(gameRepository).findById(any());
        doReturn(newRental).when(rentalRepository).save(any());

        RentalModel result = rentalService.create(rental);

        assertNotNull(result);
        verify(rentalRepository, times(1)).countByGameId(any());
        verify(rentalRepository, times(1)).save(any());
        assertEquals(newRental, result);
    }
}
