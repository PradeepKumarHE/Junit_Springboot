package com.pradeep.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.pradeep.models.Employee;
import com.pradeep.service.IEmployeeService;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;
import org.mockito.BDDMockito;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

        /*BDDMockito.given(employeeService.createEmployee(ArgumentMatchers.any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));*/

         given(employeeService.createEmployee(any(Employee.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        // when - action or the behaviour that we are going to test
        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.post("/api/employees")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(employee)));

        // then - verify the output
                /*response.andDo(MockMvcResultHandlers.print())

                .andExpect(MockMvcResultMatchers.status().isCreated())

                .andExpect(MockMvcResultMatchers.jsonPath("$.firstName",
                        CoreMatchers.is(employee.getFirstName())))

                .andExpect(MockMvcResultMatchers.jsonPath("$.lastName",
                        CoreMatchers.is(employee.getLastName())))

                .andExpect(MockMvcResultMatchers.jsonPath("$.email",
                        CoreMatchers.is(employee.getEmail())))*/

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

        //BDDMockito.given(employeeService.getAllEmployees()).willReturn(employeeList);
        given(employeeService.getAllEmployees()).willReturn(employeeList);

        // when - action or the behaviour that we are going to test
        ResultActions response= mockMvc.perform(MockMvcRequestBuilders.get("/api/employees"));

        // then - verify the output
        response
                .andExpect(status().isOk())

                .andDo(print())

                .andExpect(jsonPath("$.size()",
                        CoreMatchers.is(employeeList.size())))
        ;
    }
}
