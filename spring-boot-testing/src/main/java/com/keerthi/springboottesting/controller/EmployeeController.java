package com.keerthi.springboottesting.controller;

import com.keerthi.springboottesting.model.Employee;
import com.keerthi.springboottesting.service.EmployeeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/employees")
public class EmployeeController {

    private EmployeeService empService;

    //constructor based injection
    public EmployeeController(EmployeeService empService) {
        this.empService = empService;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Employee createEmployee(@RequestBody Employee emp) {
        return empService.saveEmployee(emp);

    }

    @GetMapping
    public List<Employee> getAllEmployee() {
        return empService.getAllEmployees();
    }

    @GetMapping("{id}")
    public ResponseEntity<Employee> getEmployeeById(@PathVariable("id") long employeeId) {
        return empService.getEmployeeById(employeeId).map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PutMapping("{id}")
    public ResponseEntity<Employee> updateEmployee(@PathVariable("id") long employeeId, @RequestBody Employee employee ){
        return empService.getEmployeeById(employeeId).map(savedEmp -> {
            savedEmp.setFirstName(employee.getFirstName());
            savedEmp.setLastName(employee.getLastName());
            savedEmp.setEmail(employee.getEmail());

            Employee updatedEmp=empService.updateEmployee(savedEmp);
            return new ResponseEntity<>(updatedEmp,HttpStatus.OK);

        }).orElseGet(()->ResponseEntity.notFound().build());

    }

    @DeleteMapping("{id}")
    public ResponseEntity<String>deleteEmployee(@PathVariable("id") long employeeId){
         empService.deleteEmployee(employeeId);
         return new ResponseEntity<String>("emp deleted succesfully",HttpStatus.OK);
    }

}
