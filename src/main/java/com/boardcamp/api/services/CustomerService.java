package com.boardcamp.api.services;

import org.springframework.stereotype.Service;

import com.boardcamp.api.dtos.CustomerDTO;
import com.boardcamp.api.exceptions.CustomerConflictException;
import com.boardcamp.api.models.CustomerModel;
import com.boardcamp.api.repositories.CustomerRepository;

@Service
public class CustomerService {
    
    final CustomerRepository customerRepository;

    CustomerService(CustomerRepository customerRepository){
        this.customerRepository = customerRepository;
    }

    public CustomerModel save(CustomerDTO dto){
        if(customerRepository.existsByCpf(dto.getCpf())){
            throw new CustomerConflictException("This customer alredy exists");
        }

        CustomerModel customer = new CustomerModel(dto);
        return customerRepository.save(customer);
    }
}
