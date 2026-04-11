package com.anastasiia.itkacademy.controller;

import java.util.List;
import java.util.UUID;

import com.anastasiia.itkacademy.controller.request.DepartmentRequest;
import com.anastasiia.itkacademy.controller.request.EmployeeRequest;
import com.anastasiia.itkacademy.entity.Department;
import com.anastasiia.itkacademy.entity.Employee;
import com.anastasiia.itkacademy.repository.EmployeeProjection;
import com.anastasiia.itkacademy.service.CompanyService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/company")
public class CompanyController {

    private final CompanyService service;

    public CompanyController(CompanyService companyService) {
        this.service = companyService;
    }

    @PostMapping("/departments")
    public ResponseEntity<Department> createDepartment(@Valid @RequestBody DepartmentRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createDepartment(request));
    }

    @GetMapping("/departments")
    public ResponseEntity<Page<Department>> getDepartments(Pageable p) {
        return ResponseEntity.ok(service.findAllDepartments(p));
    }

    @PostMapping("/employees")
    public ResponseEntity<Employee> createEmployee(@Valid @RequestBody EmployeeRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.createEmployee(request));
    }

    @GetMapping("/employees")
    public ResponseEntity<Page<Employee>> getEmployees(Pageable p) {
        return ResponseEntity.ok(service.findAllEmployees(p));
    }

    @GetMapping("/employees/projection")
    public ResponseEntity<List<EmployeeProjection>> getEmployeesProjection() {
        return ResponseEntity.ok(service.getEmployeesProjection());
    }

    @GetMapping("/employees/{id}")
    public ResponseEntity<Employee> getOneEmployee(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getEmployeeById(id));
    }

    @PutMapping("/employees/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable UUID id,
                                                   @Valid @RequestBody EmployeeRequest req) {
        return ResponseEntity.ok(service.updateEmployeeById(id, req));
    }

    @DeleteMapping("/employees/{id}")
    public ResponseEntity<Void> deleteEmployee(@PathVariable UUID id) {
        service.deleteEmployeeById(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}