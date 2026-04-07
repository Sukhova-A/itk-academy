package com.anastasiia.itkacademy.service;

import java.util.UUID;

import com.anastasiia.itkacademy.controller.converter.ProductMapper;
import com.anastasiia.itkacademy.controller.dto.ProductDto;
import com.anastasiia.itkacademy.controller.request.ProductRequest;
import com.anastasiia.itkacademy.entity.Product;
import com.anastasiia.itkacademy.repository.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository,
                          ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public Page<ProductDto> findAll(Pageable pageable) {
        return productRepository.findAll(pageable)
                .map(productMapper::toDto);
    }

    @Transactional(readOnly = true)
    public ProductDto getById(UUID id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Продукт с id %s не найден", id)));
        if (product.isDeleted()) {
            throw new RuntimeException("Продукт является удалённым");
        }
        return productMapper.toDto(product);
    }

    @Transactional
    public UUID create(ProductRequest request) {
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQuantityStock(request.getQuantityInStock());
        Product saved = productRepository.save(product);
        return saved.getId();
    }

    @Transactional
    public ProductDto updateById(UUID id, ProductRequest request) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Продукт с id %s не найден", id)));

        p.setName(request.getName());
        p.setDescription(request.getDescription());
        p.setPrice(request.getPrice());
        p.setQuantityStock(request.getQuantityInStock());

        return productMapper.toDto(p);
    }

    @Transactional
    public void delete(UUID id) {
        Product p = productRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Продукт с id %s не найден", id)));
        if (p.isDeleted()) return;
        p.setDeleted(true);
    }
}