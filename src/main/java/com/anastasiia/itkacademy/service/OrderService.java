package com.anastasiia.itkacademy.service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import com.anastasiia.itkacademy.controller.converter.OrderMapper;
import com.anastasiia.itkacademy.controller.dto.OrderDto;
import com.anastasiia.itkacademy.controller.request.OrderRequest;
import com.anastasiia.itkacademy.entity.Customer;
import com.anastasiia.itkacademy.entity.Order;
import com.anastasiia.itkacademy.entity.Product;
import com.anastasiia.itkacademy.repository.CustomerRepository;
import com.anastasiia.itkacademy.repository.OrderRepository;
import com.anastasiia.itkacademy.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final CustomerRepository customerRepository;
    private final ProductRepository productRepository;
    private final OrderMapper orderMapper;

    public OrderService(OrderRepository orderRepository,
                        CustomerRepository customerRepository,
                        ProductRepository productRepository,
                        OrderMapper orderMapper) {
        this.orderRepository = orderRepository;
        this.customerRepository = customerRepository;
        this.productRepository = productRepository;
        this.orderMapper = orderMapper;
    }

    @Transactional(readOnly = true)
    public Page<OrderDto> findAll(Pageable pageable) {
        return orderRepository.findAll(pageable)
                .map(orderMapper::toDto);
    }

    @Transactional(readOnly = true)
    public OrderDto getById(UUID id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Продукт с id %s не найден", id)));
        return orderMapper.toDto(order);
    }

    @Transactional
    public UUID create(OrderRequest request) {
        Order order = new Order();

        Customer customer = customerRepository
                .findById(request.getCustomerId())
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format("Покупатель с id %s не найден", request.getCustomerId())));
        order.setCustomer(customer);

        List<Product> products = productRepository.findAllById(request.getProductsIds());
        if (products.size() != request.getProductsIds().size()) {
            throw new EntityNotFoundException("Один или несколько продуктов не существуют");
        }
        order.setProducts(products);

        order.setOrderDate(LocalDateTime.now());
        order.setShippingAddress(request.getShippingAddress());
        order.setTotalPrice(request.getTotalPrice());
        order.setOrderStatus(request.deriveStatus());

        orderRepository.save(order);
        return order.getId();
    }

}