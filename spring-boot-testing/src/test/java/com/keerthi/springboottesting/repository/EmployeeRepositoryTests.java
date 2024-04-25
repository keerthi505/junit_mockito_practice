package com.keerthi.springboottesting.repository;

import com.keerthi.springboottesting.model.Employee;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DataJpaTest
public class EmployeeRepositoryTests {

    @Autowired
    private EmployeeRepository repo;

    //junit for save employee
    @Test
    public void givenEmployeeObject_whenSve_thenReturnSvaedEmployee(){
        //given-precondition or setup
        Employee emp=Employee.builder().firstName("keerthi").lastName("allam").email("keerthiallam10@gmail.com").build();

        //when - action or the behaviour that we are going to test

        Employee savedEmp=repo.save(emp);

        //then - verify the output

        assertThat(savedEmp).isNotNull();
        assertThat(savedEmp.getId()).isGreaterThan(0);

    }
//Jnuit test to get all employees
    @DisplayName("junit for findig all emp")
    @Test
    public void givenEmployeeList_whenFindAll_thenEmployeeList(){
        //given
        Employee emp1=Employee.builder().firstName("mohansai").lastName("rajulapati").email("keerth10@gmail.com").build();
        repo.save(emp1);
        //when
List<Employee> empList=repo.findAll();
        //then
assertThat(empList).isNotNull();
assertThat(empList.size()).isEqualTo(1);//1 record we have given so

    }

    //Junit test case for get emp by id operation
@Test
    public void givenEmployeeObject_whenFindById_thenReturnEmployeeObject(){
        Employee emp2=Employee.builder().firstName("kiara").lastName("sjfvksv").email("keerthknv10@gmail.com").build();
        repo.save(emp2);

        Employee empDb=repo.findById(emp2.getId()).get();

        assertThat(empDb.getId()).isNotNull();
    }

    //Junit test for get employee by email operation

    @Test
    public void given_when_then(){
        Employee emp3=Employee.builder().firstName("kiara").lastName("sjfvksv").email("keerthknv10@gmail.com").build();
        repo.save(emp3);

        Optional<Employee> byEmail = repo.findByEmail(emp3.getEmail());

        assertThat(byEmail).isNotNull();


    }


    //Junit Test case for updating employee


    @Test
    public void given_whenSve_thenReturnSvaedEmployee(){
        //given-precondition or setup
        Employee emp4=Employee.builder().firstName("keerthi").lastName("allam").email("keerthiallam10@gmail.com").build();

        Employee savedEmp=repo.save(emp4);
        //when - action or the behaviour that we are going to test
        Employee employee = repo.findById(emp4.getId()).get();
        employee.setFirstName("john");
        employee.setLastName("cena");
        repo.save(employee);

        //then - verify the output

        assertThat(employee.getFirstName()).isEqualTo("john");
        assertThat(employee.getLastName()).isEqualTo("cena");


    }

    //junit to test delete employee
    @Test
    public void given_whenSnve_thenReturnSvaedEmployee(){
        //given-precondition or setup
        Employee emp5=Employee.builder().firstName("keerthi").lastName("allam").email("keerthiallam10@gmail.com").build();

        Employee savedEmp=repo.save(emp5);
        //when - action or the behaviour that we are going to test
        repo.delete(emp5);
        Optional<Employee> delEmp=repo.findById(emp5.getId());



        //then - verify the output

        assertThat(delEmp).isEmpty();



    }

     //Junit test for custom query using custom jpql queries with index

    @Test
    public void givenFirstName_whenFindByjpql_thenReturnEmpObj(){
        Employee emp6=Employee.builder().firstName("keerthi").lastName("allam").email("keerthiallam10@gmail.com").build();

        repo.save(emp6);
        String firstName = "keerthi";
        String lastName = "allam";

        Employee savedEmp=repo.findByJpql(firstName,lastName);

        assertThat(savedEmp).isNotNull();


    }

    //Junit test for custom query using custom jpql queries with named parameters

    @Test
    public void givenFirstName_whenFindByjpqlNamedPrams_thenReturnEmpObj(){
        Employee emp7=Employee.builder().firstName("keerthi").lastName("allam").email("keerthiallam10@gmail.com").build();

        repo.save(emp7);
        String firstName = "keerthi";
        String lastName = "allam";

        Employee savedEmp=repo.findByJpqlNamedParams(firstName,lastName);

        assertThat(savedEmp).isNotNull();


    }




}
