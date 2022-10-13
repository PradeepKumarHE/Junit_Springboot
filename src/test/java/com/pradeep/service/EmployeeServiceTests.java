package com.pradeep.service;

import com.pradeep.models.Employee;
import com.pradeep.repository.EmployeeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import static org.mockito.BDDMockito.given;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

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
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
    }

    // Junit Tests for custom query using jqpql operation
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
}
