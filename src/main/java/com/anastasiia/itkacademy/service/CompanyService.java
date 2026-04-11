package com.anastasiia.itkacademy.service;

import java.util.List;
import java.util.UUID;

import com.anastasiia.itkacademy.controller.request.DepartmentRequest;
import com.anastasiia.itkacademy.controller.request.EmployeeRequest;
import com.anastasiia.itkacademy.entity.Department;
import com.anastasiia.itkacademy.entity.Employee;
import com.anastasiia.itkacademy.repository.DepartmentRepository;
import com.anastasiia.itkacademy.repository.EmployeeProjection;
import com.anastasiia.itkacademy.repository.EmployeeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CompanyService {

    private final EmployeeRepository employeeRepository;
    private final DepartmentRepository departmentRepository;

    public CompanyService(EmployeeRepository employeeRepository,
                          DepartmentRepository departmentRepository) {
        this.employeeRepository = employeeRepository;
        this.departmentRepository = departmentRepository;
    }

    @Transactional
    public Department createDepartment(DepartmentRequest request) {
        Department department = new Department();
        department.setName(request.name());
        return departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public Department getDepartmentById(UUID id) {
        return departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Отдел с id %s не найден", id)));
    }

    @Transactional
    public void deleteDepartmentById(UUID id) {
        departmentRepository.deleteById(id);
    }

    @Transactional
    public Department updateDepartmentById(UUID id, DepartmentRequest request) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Отдел с id %s не найден", id)));
        department.setName(request.name());
        return departmentRepository.save(department);
    }

    @Transactional(readOnly = true)
    public Page<Department> findAllDepartments(Pageable pageable) {
        return departmentRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Page<Employee> findAllEmployees(Pageable pageable) {
        return employeeRepository.findAll(pageable);
    }

    @Transactional(readOnly = true)
    public Employee getEmployeeById(UUID id) {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(String.format("Сотрудник с id %s не найден", id)));
    }

    @Transactional
    public void deleteEmployeeById(UUID id) {
        employeeRepository.deleteById(id);
    }

    @Transactional
    public Employee createEmployee(EmployeeRequest request) {
        var department = departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Отдел с id %s не найден", request.getDepartmentId())));

        Employee employee = new Employee();
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());
        employee.setDepartment(department);
        return employeeRepository.save(employee);
    }

    @Transactional
    public Employee updateEmployeeById(UUID id, EmployeeRequest request) {
        Employee employee = getEmployeeById(id);
        employee.setDepartment(departmentRepository.findById(request.getDepartmentId())
                .orElseThrow(() -> new EntityNotFoundException(String.format("Отдел с id %s не найден", request.getDepartmentId()))));
        employee.setFirstName(request.getFirstName());
        employee.setLastName(request.getLastName());
        employee.setPosition(request.getPosition());
        employee.setSalary(request.getSalary());

        return employeeRepository.save(employee);
    }


    @Transactional(readOnly = true)
    public List<EmployeeProjection> getEmployeesProjection() {
        return employeeRepository.findAllProjectedBy();
    }
}