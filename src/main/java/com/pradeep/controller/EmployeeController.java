package com.pradeep.controller;

import com.pradeep.models.Employee;
import com.pradeep.service.IEmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {
    private IEmployeeService employeeService;

    public EmployeeController(IEmployeeService employeeService){
        this.employeeService=employeeService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee employee){
        return employeeService.createEmployee(employee);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Employee> createEmployee(){
        return employeeService.getAllEmployees();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeid){
        return employeeService.getEmployeeById(employeeid).map(ResponseEntity::ok).orElseGet(()->ResponseEntity.notFound().build());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeid,@RequestBody Employee employee){
        return employeeService.getEmployeeById(employeeid).map(savedEmployee -> {
            savedEmployee.setFirstName(employee.getFirstName());
            savedEmployee.setLastName(employee.getLastName());
            savedEmployee.setEmail(employee.getEmail());
            Employee updatedEmployee=employeeService.updateEmployee(savedEmployee);
            return new ResponseEntity<>(updatedEmployee,HttpStatus.OK);
        }).orElseGet(()->ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteEmployee(@PathVariable("id") long employeeid){
        employeeService.deleteEmployee(employeeid);
        return new ResponseEntity<String>("Employee deleted successfully",HttpStatus.OK);
    }

}
