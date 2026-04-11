package com.anastasiia.itkacademy.repository;

import java.util.List;
import java.util.UUID;

import com.anastasiia.itkacademy.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {

    @Query("select e from Employee e join fetch e.department")
    List<EmployeeProjection> findAllProjectedBy();
}