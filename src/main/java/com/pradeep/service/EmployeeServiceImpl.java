package com.pradeep.service;

import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.models.Employee;
import com.pradeep.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmployeeServiceImpl implements IEmployeeService{


    private final EmployeeRepository employeeRepository;

    public EmployeeServiceImpl(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }

    @Override
    public Employee createEmployee(Employee employee) {
       Optional<Employee> optionalEmployee=employeeRepository.findByEmail(employee.getEmail());
       if(optionalEmployee.isPresent()){
           throw new ResourceNotFoundException("Employee exists :: "+employee.getEmail());
       }
        return employeeRepository.save(employee);
    }
}
