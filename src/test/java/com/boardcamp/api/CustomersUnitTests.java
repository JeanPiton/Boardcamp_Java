package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.CustomerConflictException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;
import com.boardcamp.api.services.CustomerService;

@SpringBootTest
class CustomersUnitTests {
    
    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    @Test
    void givenRepeatedCpf_whenCreatingCustomer_thenThrowsError() {
        CustomerDTO customerDTO = new CustomerDTO("test", "11111111111");
        doReturn(true).when(customerRepository).existsByCpf(any());

        CustomerConflictException exception = assertThrows(CustomerConflictException.class, () -> customerService.save(customerDTO));

        assertNotNull(exception);
        assertEquals("This customer alredy exists", exception.getMessage());
        verify(customerRepository, times(0)).save(any());
    }

    @Test
	void givenValidCustomer_whenCreatingCustomer_thenThrowsError() {
		CustomerDTO customerDTO = new CustomerDTO("test","11111111111");
		CustomerModel newCustomer = new CustomerModel(customerDTO);
		doReturn(false).when(customerRepository).existsByCpf(any());
		doReturn(newCustomer).when(customerRepository).save(any());

		CustomerModel result = customerService.save(customerDTO);

		assertNotNull(result);
		verify(customerRepository, times(1)).existsByCpf(any());
		verify(customerRepository, times(1)).save(any());
		assertEquals(result, newCustomer);
	}

}
