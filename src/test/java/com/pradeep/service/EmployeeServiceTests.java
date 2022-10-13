package com.pradeep.service;

import com.pradeep.exceptions.ResourceNotFoundException;
import com.pradeep.models.Employee;
import com.pradeep.repository.EmployeeRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.*;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository employeeRepository;

    @InjectMocks
    private EmployeeServiceImpl employeeService;

    private  Employee employee;
    @BeforeEach
    public void setup(){
        employee=Employee.builder()
                .id(1L)
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
    }

    // Junit Tests for Create Employee
    @DisplayName("CreateEmployeeServiceTest")
    @Test
    public void givenEmployeeObj_whenSave_thenReturnSavedEmpObject(){

        // given(setup)
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.empty());
        given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee savedEmployee=employeeService.createEmployee(employee);

        //then verify output
        assertThat(savedEmployee).isNotNull();
    }

    // Junit Tests for Checking employee while creating employee
    @DisplayName("CreateEmployeeServiceNegativeScenarioTest")
    @Test
    public void givenExistingEmail_whenSave_thenThrowsException(){

        // given(setup)
        given(employeeRepository.findByEmail(employee.getEmail())).willReturn(Optional.of(employee));

        Assertions.assertThrows(ResourceNotFoundException.class,()->{
            employeeService.createEmployee(employee);
        });

      //then
        verify(employeeRepository,never()).save(any(Employee.class));
    }

    // Junit Tests for getAllEmployees
    @DisplayName("GetAllEmploeesServiceTest")
    @Test
    public void givenNothing_whenGetAllEmployees_thenEmployeeList(){

        // given(setup)
        Employee employee2=Employee.builder()
                .firstName("Pradeep")
                .lastName("Kumar")
                .email("pradeep@gmail.com")
                .build();
        given(employeeRepository.findAll()).willReturn(List.of(employee,employee2));

        //when
        List<Employee> employeeList=employeeService.getAllEmployees();

        //then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isGreaterThan(0);
    }

    // Junit Tests for getAllEmployees Negative Scenario
    @DisplayName("GetAllEmploeesServiceNegativeScenarioTest")
    @Test
    public void givenEmptyEmpList_whenGetAllEmployees_thenReturnEmpEmployeeList(){

        // given(setup)

        given(employeeRepository.findAll()).willReturn(Collections.emptyList());

        //when
        List<Employee> employeeList=employeeService.getAllEmployees();

        //then
        assertThat(employeeList).isEmpty();
        assertThat(employeeList.size()).isEqualTo(0);
    }

    // Junit Tests for getEmployeeById
    @DisplayName("getEmployeeByIdServiceTest")
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObj(){

        // given(setup)

        given(employeeRepository.findById(1L)).willReturn(Optional.of(employee));

        //when
       Employee savedemployee=employeeService.getEmployeeById(employee.getId()).get();

        //then
        assertThat(savedemployee).isNotNull();
    }

    // Junit Tests for updateEmployee
    @DisplayName("UpdateEmployeeServiceTest")
    @Test
    public void givenEmployeeObj_whenUpdateEmployee_thenReturnUpdatedEmployeeObj(){

        // given(setup)

        given(employeeRepository.save(employee)).willReturn(employee);
        employee.setEmail("nandinism@gmail.com");
        //when
        Employee updatedemployee=employeeService.updateEmployee(employee);

        //then
        assertThat(updatedemployee.getEmail()).isEqualTo("nandinism@gmail.com");
    }

    // Junit Tests for Delete Employee By Id
    @DisplayName("DeleteEmployeeServiceTest")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing(){

        // given(setup)
        willDoNothing().given(employeeRepository).deleteById(1L);

        //when
       employeeService.deleteEmployee(1L);

        //then
        verify(employeeRepository,times(1)).deleteById(1L);
    }
}
