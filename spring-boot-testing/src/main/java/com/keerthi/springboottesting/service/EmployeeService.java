package com.keerthi.springboottesting.service;

import com.keerthi.springboottesting.model.Employee;

import java.util.List;
import java.util.Optional;

public interface EmployeeService {
    //save employee
    Employee saveEmployee(Employee employee);
    //get all employees

    List<Employee> getAllEmployees();

    //get by id
    Optional<Employee> getEmployeeById(long id);

    //update emp

    Employee updateEmployee(Employee employee);

    //delete by id
    void deleteEmployee(long id);

}
