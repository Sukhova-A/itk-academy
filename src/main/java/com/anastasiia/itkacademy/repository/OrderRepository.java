package com.anastasiia.itkacademy.repository;

import java.util.UUID;

import com.anastasiia.itkacademy.entity.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
}