package com.pradeep.service;

import com.pradeep.models.Employee;

import java.util.List;
import java.util.Optional;

public interface IEmployeeService {

    Employee createEmployee(Employee employee);

    List<Employee> getAllEmployees();

    Optional<Employee> getEmployeeById(long employeeId);

    Employee updateEmployee(Employee employee);

    void deleteEmployee(Long employeeId);

}
