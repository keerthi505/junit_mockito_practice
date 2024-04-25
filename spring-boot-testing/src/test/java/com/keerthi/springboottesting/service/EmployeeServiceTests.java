package com.keerthi.springboottesting.service;

import com.keerthi.springboottesting.exception.ResourceNotFoundException;
import com.keerthi.springboottesting.model.Employee;
import com.keerthi.springboottesting.repository.EmployeeRepository;
import com.keerthi.springboottesting.service.impl.EmployeeServiceImpl;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class EmployeeServiceTests {

    @Mock
    private EmployeeRepository repo;

    @InjectMocks
    //creates the mock obj and inject the mock that are marked with @mock annotation
    private EmployeeServiceImpl service;

    private Employee emp;//to make emp obj available to all classes

    @BeforeEach
    public void setUp() {
        //repo= Mockito.mock(EmployeeRepository.class);
        //service=new EmployeeServiceImpl(repo);//injecting repo into service
        emp = Employee.builder().id(1L).firstName("k").lastName("r").email("keerthiallam10@gmail.com").build();

    }

    //Junit test for saveEmployee method which do not throw exception
    @Test
    public void givenEmployeeObj_whenSaveEmployee_thenReturnEmployeeObject() {

        //given

        //stub 2 methods present

        given(repo.findByEmail(emp.getEmail())).willReturn(Optional.empty());
        given(repo.save(emp)).willReturn(emp);
        //to check whether they are succesfully mocked or not

        System.out.println(repo);
        System.out.println(service);
        //when
        Employee savedEmp = repo.save(emp);
        System.out.println(savedEmp);
        assertThat(savedEmp).isNotNull();


    }

    //Junit test for saveEmployee method throw exception it means employee id is present
    @Test
    public void givenExsistingEmal_whenSaveEmployee_thenThrowsException() {

        //given

        //stub 2 methods present

        given(repo.findByEmail(emp.getEmail())).willReturn(Optional.of(emp));

        //to check whether they are succesfully mocked or not

        //when
        org.junit.jupiter.api.Assertions.assertThrows(ResourceNotFoundException.class, () -> {
            service.saveEmployee(emp);
        });

        //then
        //after throwing exception control should not go to save method so

        verify(repo, never()).save(any(Employee.class));


    }

    //Junit test for getAllEmployess method
    @Test
    public void getAllEmployeesList_whenGetAllEmployees_thenReturnEmployeeList() {
        //given
        Employee emp1 = Employee.builder().id(2L).firstName("kiusjd").lastName("r").email("keertnhhiallam10@gmail.com").build();
        given(repo.findAll()).willReturn(List.of(emp, emp1));

        //when
        List<Employee> empList = service.getAllEmployees();

        //then

        assertThat(empList).isNotNull();
        assertThat(empList.size()).isEqualTo(2);

    }

    //Junit test for getAllEmployess method negative scenario
//negative scenario
    @Test
    public void givenListOfEmployess_whenGetAllEmployees_thenReturnEmptyEmployeeList() {
        Employee emp1 = Employee.builder().id(2L).firstName("kiusjd").lastName("r").email("keertnhhiallam10@gmail.com").build();
        given(repo.findAll()).willReturn(Collections.emptyList());

        //when
        List<Employee> empList = service.getAllEmployees();

        //then

        assertThat(empList).isEmpty();
        assertThat(empList.size()).isEqualTo(0);
    }
//junit test case for finding employee by id

    @Test
    public void givenEmployeeId_whenGrtEmployeeById_thenReturnEmptyEmployeeList() {
        Employee emp1 = Employee.builder().id(2L).firstName("kiusjd").lastName("r").email("keertnhhiallam10@gmail.com").build();
        given(repo.findAll()).willReturn(Collections.emptyList());

        //when
        List<Employee> empList = service.getAllEmployees();

        //then

        assertThat(empList).isEmpty();
        assertThat(empList.size()).isEqualTo(0);
    }

    //Junit test for getEmployeeById
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeObject() {
        //given
        given(repo.findById(1L)).willReturn(Optional.of(emp));


        //when
        Employee getEmpById = service.getEmployeeById(emp.getId()).get();

        //then

        assertThat(getEmpById).isNotNull();
    }

    @Test
    public void givenEmployeeObject_whenUpdateEmployee_thenReturnEmployeeObject() {
        //given
        given(repo.save(emp)).willReturn(emp);
        emp.setFirstName("John");
        emp.setLastName("fj");


        //when
        Employee e = service.updateEmployee(emp);

        //then
        assertThat(e.getFirstName()).isEqualTo("John");
        assertThat((e.getLastName())).isEqualTo("fj");
    }

    //Junit test for dleteEmployee method
    @Test
    public void givenEmployeeId_whenDeleteEmployeeId_thenDeleteEmployee() {
        //given
        long id = 1L;
        //when stubbing method which returns nothing

        willDoNothing().given(repo).deleteById(1L);
        //when

        service.deleteEmployee(1L);
        //then

        //as it does not return anything, we can check how many times methd has been called

        verify(repo, times(1)).deleteById(id);
    }

    @Test
    public void givenListOfEmployees_when_thenDelGetAllEmployee_thenReturnEmployeeList() {
        //given
        long id = 1L;
        //when stubbing method which returns nothing

        willDoNothing().given(repo).deleteById(1L);
        //when

        service.deleteEmployee(1L);
        //then

        //as it does not return anything, we can check how many times methd has been called

        verify(repo, times(1)).deleteById(id);
    }
}
