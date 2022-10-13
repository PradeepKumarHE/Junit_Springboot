package com.pradeep.service;

import com.pradeep.models.Employee;
import com.pradeep.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import  org.mockito.BDDMockito;
import org.mockito.Mockito;
import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


public class EmployeeServiceTests {

    private EmployeeRepository employeeRepository;
    private IEmployeeService employeeService;

    @BeforeEach
    public void setup(){
        employeeRepository= Mockito.mock(EmployeeRepository.class);
        employeeService=new EmployeeServiceImpl(employeeRepository);
    }

    // Junit Tests for custom query using jqpql operation
    @DisplayName("CreateEmployeeServiceTest")
    @Test
    public void givenEmployeeObj_whenSave_thenReturnSavedEmpObject(){

        // given(setup)
        Employee employee=Employee.builder()
                .id(1L)
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();

        BDDMockito.given(employeeRepository.findByEmail(employee.getEmail()))
                .willReturn(Optional.empty());
        BDDMockito.given(employeeRepository.save(employee)).willReturn(employee);

        // when
        Employee savedEmployee=employeeService.createEmployee(employee);

        //then verify output
        assertThat(savedEmployee).isNotNull();
    }
}
