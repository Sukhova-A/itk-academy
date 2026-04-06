package com.anastasiia.itkacademy.service;

import java.util.UUID;

import com.anastasiia.itkacademy.controller.dto.CustomerDto;
import com.anastasiia.itkacademy.entity.Customer;
import com.anastasiia.itkacademy.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional(readOnly = true)
    public CustomerDto getById(UUID id) {
        Customer order = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Покупатель с id %s не найден", id)));
        return mapToDto(order);
    }

    private CustomerDto mapToDto(Customer c) {
        return new CustomerDto()
                .setId(c.getId())
                .setFirstName(c.getFirstName())
                .setLastName(c.getLastName())
                .setEmail(c.getEmail())
                .setNumber(c.getNumber());
    }
}