package com.pradeep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pradeep.models.Employee;
import com.pradeep.service.IEmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IEmployeeService employeeService;

    @Autowired
    private ObjectMapper objectMapper;

    private  Employee employee;

    @BeforeEach
    private void setUp(){
        employee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
    }
    @DisplayName("CreateEmployee")
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {

        // given - pre condition or setup
         given(employeeService.createEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or the behaviour that we are going to test
        //ResultActions response= mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
        ResultActions response= mockMvc.perform(post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the output

                response.andDo(print())

                .andExpect(status().isCreated())

                .andExpect(jsonPath("$.firstName",
                        CoreMatchers.is(employee.getFirstName())))

                .andExpect(jsonPath("$.lastName",
                        CoreMatchers.is(employee.getLastName())))

                .andExpect(jsonPath("$.email",
                        CoreMatchers.is(employee.getEmail())))
        ;
    }

    @DisplayName("getAllEmployees")
    @Test
    public void giveEmplist_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        // given - pre condition or setup

        List<Employee> employeeList=new ArrayList<Employee>();
        Employee employee2=Employee.builder()
                .firstName("Pradeep")
                .lastName("Kumar")
                .email("pradeep@gmail.com")
                .build();
        employeeList.add(employee);
        employeeList.add(employee2);

        given(employeeService.getAllEmployees()).willReturn(employeeList);

        // when - action or the behaviour that we are going to test
        ResultActions response= mockMvc.perform(get("/api/employees"));

        // then - verify the output
        response
                .andExpect(status().isOk())

                .andDo(print())

                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(employeeList.size())))
        ;
    }

    @DisplayName("getEmployeeById")
    @Test
    public void giveEmployeeId_whenGetEmployeeById_thenReturnEmployeeObj() throws Exception {

        // given - pre condition or setup
        long employeeId=1L;

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        // when - action or the behaviour that we are going to test
        ResultActions response= mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response
                .andExpect(status().isOk())

                .andDo(print())

                .andExpect(jsonPath("$.firstName",
                        CoreMatchers.is(employee.getFirstName())))

                .andExpect(jsonPath("$.lastName",
                        CoreMatchers.is(employee.getLastName())))

                .andExpect(jsonPath("$.email",
                        CoreMatchers.is(employee.getEmail())))
        ;
    }

    @DisplayName("getEmployeeByIdNegativeScenario")
    @Test
    public void giveInavalidEmployeeId_whenGetEmployeeById_thenReturnEmpty() throws Exception {

        // given - pre condition or setup
        long employeeId=1L;

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        // when - action or the behaviour that we are going to test
        ResultActions response= mockMvc.perform(get("/api/employees/{id}", employeeId));

        // then - verify the output
        response
                .andExpect(status().isNotFound())
                .andDo(print());
    }

    @DisplayName("UpdateEmployeeTest")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturnSavedUpdatedEmployee() throws Exception {

        // given - pre condition or setup
        long employeeId=1L;

        Employee updatedEmployee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("nandinism@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(employee));

        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or the behaviour that we are going to test
        ResultActions response= mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee))
        );

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andDo(print())

                .andExpect(jsonPath("$.email",
                        CoreMatchers.is(updatedEmployee.getEmail())))
        ;
    }

    @DisplayName("UpdateEmployeeTestNegativeScenario")
    @Test
    public void givenUpdatedEmployee_whenUpdateEmployee_thenReturn404() throws Exception {

        // given - pre condition or setup
        long employeeId=1L;

        Employee updatedEmployee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("nandinism@gmail.com")
                .build();

        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        given(employeeService.updateEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or the behaviour that we are going to test
        ResultActions response= mockMvc.perform(put("/api/employees/{id}", employeeId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(updatedEmployee))
        );

        // then - verify the output
        response
                .andExpect(status().isNotFound())
                .andDo(print())
        ;
    }

    @DisplayName("deleteEmployee")
    @Test
    public void givenEmployeeId_whenDeleteEmployee_thenReturnNothing200() throws Exception {

        // given - pre condition or setup
        long employeeId=1L;

        willDoNothing().given(employeeService).deleteEmployee(employeeId);

        // when - action or the behaviour that we are going to test
        ResultActions response= mockMvc.perform(delete("/api/employees/{id}", employeeId));

        // then - verify the output
        response
                .andExpect(status().isOk())
                .andDo(print());
    }
}
