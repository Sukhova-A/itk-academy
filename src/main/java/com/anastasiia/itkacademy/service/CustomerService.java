package com.anastasiia.itkacademy.service;

import java.util.UUID;

import com.anastasiia.itkacademy.controller.converter.CustomerMapper;
import com.anastasiia.itkacademy.controller.dto.CustomerDto;
import com.anastasiia.itkacademy.entity.Customer;
import com.anastasiia.itkacademy.repository.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    public CustomerService(CustomerRepository customerRepository,
                           CustomerMapper customerMapper) {
        this.customerRepository = customerRepository;
        this.customerMapper = customerMapper;
    }

    @Transactional(readOnly = true)
    public CustomerDto getById(UUID id) {
        Customer customer = customerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Покупатель с id %s не найден", id)));
        return customerMapper.toDto(customer);
    }
}