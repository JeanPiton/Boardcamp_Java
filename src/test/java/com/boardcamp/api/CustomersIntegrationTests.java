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

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class CustomersIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private CustomerRepository customerRepository;

    @BeforeEach
    @AfterEach
    void cleanUpDatabase() {
        customerRepository.deleteAll();
    }

    @Test
	void givenRepeatedCpf_whenCreatingCustomer_thenThrowsError() {
		CustomerDTO customerDTO = new CustomerDTO("test","11111111111");
		CustomerModel customerConflict = new CustomerModel(customerDTO);
        customerRepository.save(customerConflict);
        HttpEntity<CustomerDTO> body = new HttpEntity<>(customerDTO);
		
        ResponseEntity<String> response = restTemplate.exchange("/customers", HttpMethod.POST,body,String.class);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(1, customerRepository.count());
	}

    @Test
	void givenValidCustomer_whenCreatingCustomer_thenThrowsError() {
		CustomerDTO customerDTO = new CustomerDTO("test","11111111111");
        HttpEntity<CustomerDTO> body = new HttpEntity<>(customerDTO);
		
        ResponseEntity<String> response = restTemplate.exchange("/customers", HttpMethod.POST,body,String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, customerRepository.count());
	}
}
