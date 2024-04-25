package com.keerthi.springboottesting.repository;

import com.keerthi.springboottesting.model.Employee;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    //Note: JpaRepository has SimpleJpaRepository which internally implements @Repository and @Transcactional so we dont neeed to annotate

    //by default all the method of jpa repo are transactional

    //for testing repo layer we are not using mockito as repo layer doest not  depend on any other layers

    //for repo we have @DataJpaTest annotation to internally provides im mem db for testing purposes

    //custom query

   //define custom query using jpql with index paramaeters
   Optional<Employee> findByEmail(String email);
   @Query("select e from Employee e where e.firstName = ?1 and e.lastName = ?2")
   Employee findByJpql(String firstName , String lastName);


   //Define cutom query using named parameters
   @Query("select e from Employee e where e.firstName=:firstName and e.lastName=:lastName")
   Employee findByJpqlNamedParams(@Param("firstName") String firstName , @Param("lastName")String lastName);


}
