package com.keerthi.springboottesting.service.impl;

import com.keerthi.springboottesting.exception.ResourceNotFoundException;
import com.keerthi.springboottesting.model.Employee;
import com.keerthi.springboottesting.repository.EmployeeRepository;
import com.keerthi.springboottesting.service.EmployeeService;
import jakarta.annotation.Resource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class EmployeeServiceImpl implements EmployeeService {

    private EmployeeRepository repo;
    public EmployeeServiceImpl(EmployeeRepository repo) {
        this.repo = repo;
    }

    public Employee saveEmployee(Employee employee){
        Optional<Employee> byEmail = repo.findByEmail(employee.getEmail());

        if(byEmail.isPresent()){
            throw new ResourceNotFoundException("employee already present"+employee.getEmail());

        }
        return repo.save(employee);

    }

    @Override
    public List<Employee> getAllEmployees() {
        return repo.findAll();
    }

    @Override
    public Optional<Employee> getEmployeeById(long id) {
        return repo.findById(id);
    }

    @Override
    public Employee updateEmployee(Employee employee) {
        return repo.save(employee);
    }

    @Override
    public void deleteEmployee(long id) {
        repo.deleteById(id);
    }


}
