package com.pradeep.reository;

import com.pradeep.models.Employee;
import com.pradeep.repository.EmployeeRepository;
import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository employeeRepository;

    // Junit Tests for save employee operation

    @DisplayName("Junit Tests for save employee operation")
    @Test
    public void givenEmployeeObject_whenSave_thenReturnSavedEmployee(){
        // given - pre condition or setup
        Employee employee=Employee.builder()
                .firstName("Pradeep")
                .lastName("Kumar")
                .email("pradeepkumarhe1989@gmail.com")
                .build();

        // when - action or the behaviour that we are going to test
        Employee savedEmployee=employeeRepository.save(employee);

        // then - verify the output
        assertThat(savedEmployee).isNotNull();
        assertThat(savedEmployee.getId()).isGreaterThan(0);
    }

    // Junit Tests for get employees operation
    @Test
    public void givenTwoEmployeesObj_whenFindALL_thenEmplist(){
        // given(setup)
        Employee employee1=Employee.builder()
                .firstName("Pradeep")
                .lastName("Kumar")
                .email("pradeepkumarhe1989@gmail.com")
                .build();

        Employee employee2=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
        employeeRepository.save(employee1);
        employeeRepository.save(employee2);
        // when
         List<Employee> employeeList=employeeRepository.findAll();
        //then
        assertThat(employeeList).isNotNull();
        assertThat(employeeList.size()).isEqualTo(2);
    }

    // Junit Tests for get employee by id operation
    @Test
    public void givenEmployeesObj_whenFindById_thenReturnEmpObject(){

        // given(setup)
        Employee employee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when
        Employee savedemployee=employeeRepository.findById(employee.getId()).get();

        //then
        assertThat(savedemployee).isNotNull();
    }

    // Junit Tests for get employee by email operation
    @Test
    public void givenEmployeeEmail_whenFindByEmail_thenReturnEmpObject(){

        // given(setup)
        Employee employee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when
        Employee savedemployee=employeeRepository.findByEmail(employee.getEmail()).get();

        //then
        assertThat(savedemployee).isNotNull();
    }

    // Junit Tests for update employee operation
    @Test
    public void givenEmployeeObj_whenUpdateEmployee_thenReturnUpdatedEmpObject(){

        // given(setup)
        Employee employee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when
        Employee savedemployee=employeeRepository.findById(employee.getId()).get();

        savedemployee.setEmail("nandinism@gmail.com");

        Employee updatedemployee=employeeRepository.save(savedemployee);

        //then
        assertThat(updatedemployee.getEmail()).isEqualTo("nandinism@gmail.com");
    }

    // Junit Tests for delete employee operation
    @Test
    public void givenEmployeeObj_whenDelete_thenRemoveFromDB(){

        // given(setup)
        Employee employee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
        employeeRepository.save(employee);

        // when
        employeeRepository.delete(employee);
        Optional<Employee> savedemployee=employeeRepository.findById(employee.getId());

        //then
        assertThat(savedemployee).isEmpty();
    }

    // Junit Tests for custom query using jqpql operation
    @Test
    public void givenFirstNameLastName_whenFindByJPQL_thenReturnEmpObject(){

        // given(setup)
        Employee employee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
        employeeRepository.save(employee);

        String firstName="Nandini";
        String lastName="SM";

        // when
        Employee savedemployee=employeeRepository.findByJPQL(firstName,lastName);

        //then
        assertThat(savedemployee).isNotNull();
    }

    // Junit Tests for custom query using jqpql named params operation
    @Test
    public void givenFirstNameLastName_whenFindByJPQLNamedParams_thenReturnEmpObject(){

        // given(setup)
        Employee employee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
        employeeRepository.save(employee);

        String firstName="Nandini";
        String lastName="SM";

        // when
        Employee savedemployee=employeeRepository.findByJPQLNamedParams(firstName,lastName);

        //then
        assertThat(savedemployee).isNotNull();
    }

    // Junit Tests for NativeSQL query operation
    @Test
    public void givenFirstNameLastName_whenFindByNativeSQL_thenReturnEmpObject(){

        // given(setup)
        Employee employee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
        employeeRepository.save(employee);

        String firstName="Nandini";
        String lastName="SM";

        // when
        Employee savedemployee=employeeRepository.findByNativeSQL(firstName,lastName);

        //then
        assertThat(savedemployee).isNotNull();
    }

    // Junit Tests for NativeSQL Named Params query operation
    @Test
    public void givenFirstNameLastName_whenFindByNativeSQLNamedParams_thenReturnEmpObject(){

        // given(setup)
        Employee employee=Employee.builder()
                .firstName("Nandini")
                .lastName("SM")
                .email("smnandini@gmail.com")
                .build();
        employeeRepository.save(employee);

        String firstName="Nandini";
        String lastName="SM";

        // when
        Employee savedemployee=employeeRepository.findByNativeSQLNamedParams(firstName,lastName);

        //then
        assertThat(savedemployee).isNotNull();
    }

}
