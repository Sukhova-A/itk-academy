package com.anastasiia.itkacademy.repository;

import java.util.UUID;

import com.anastasiia.itkacademy.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, UUID> {
}