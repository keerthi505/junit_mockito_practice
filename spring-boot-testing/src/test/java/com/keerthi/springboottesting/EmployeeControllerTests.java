package com.keerthi.springboottesting;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.keerthi.springboottesting.model.Employee;
import com.keerthi.springboottesting.service.EmployeeService;

import static org.hamcrest.CoreMatchers.is;

import org.junit.jupiter.api.Test;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcResultMatchersDsl;
import org.springframework.test.web.servlet.ResultActions;

import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest
public class EmployeeControllerTests {

    @Autowired
    private MockMvc mockMvc; //to call rest apis

    @MockBean // create a mock instance of EmpService and add it to
    //application context so that its injected to Employee Controller

    private EmployeeService employeeService;

    @Autowired //to serialize and deserialize java objects
    private ObjectMapper objectMapper;

    //Jnuit test for Save Employee
    @Test
    public void givenEmployeeObject_whenCreateEmployee_thenReturnSavedEmployee() throws Exception {
        //given
        Employee emp = Employee.builder().firstName("John").lastName("C").email("john@gmail.com").build();


        //stub
        given(employeeService.saveEmployee(any(Employee.class))).willAnswer(invocation -> invocation.getArgument(0));//since it has one argument


        //when

        //to call rest api

        ResultActions perform = mockMvc.perform(post("/api/employees").contentType(MediaType.APPLICATION_JSON).
                //to convert object onto json
                        content(objectMapper.writeValueAsString(emp)));
        //then

        perform.andDo(print()).andExpect(status().isCreated()).
                andExpect(jsonPath("$.firstName", is(emp.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(emp.getLastName())))
                .andExpect(jsonPath("$.email", is(emp.getEmail())));
    }

    //Jnuit test for Get ALL employees REST API

    @Test
    public void given_ListOfEmployees_whenGetAllEmployees_thenReturnEmployeeList() throws Exception {
        //given
        List<Employee> li = new ArrayList<>();
        li.add(Employee.builder().firstName("John").lastName("Allam").email("keerthiallam10@gmail.com").build());

        li.add(Employee.builder().firstName("k").lastName("s").email("ncb@gmail.com").build());
        given(employeeService.getAllEmployees()).willReturn(li);

        //when
        ResultActions response = mockMvc.perform(get("/api/employees"));


        //then

        response.andExpect(status().isOk()).
                andDo(print())//print the response of rest api
                .andExpect(jsonPath("$.size()", is(li.size())));
    }


    //Jnuit for passing valid emp id
    @Test
    public void givenEmployeeId_whenGetEmployeeId_thenReturnEMployeeById() throws Exception {


        //given

        long employeeId = 1L;
        Employee emp2 = Employee.builder().firstName("John").lastName("jjf").email("ns@gmail.com").build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(emp2));

        //when
        ResultActions r = mockMvc.perform(get("/api/employees/{id}", employeeId));

        //verify

        r.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.firstName", is(emp2.getFirstName())))
                .andExpect(jsonPath("$.lastName", is(emp2.getLastName())))
                .andExpect(jsonPath("$.email", is(emp2.getEmail())));


    }

    //Junit for invalid employee id
    @Test
    public void givenEmployeeId_whenGetEmployeeById_thenReturnEmployeeId() throws Exception {
        //given
        long employeeId = 1L;
        Employee emp3 = Employee.builder().firstName("John").lastName("C").email("asjdd@gmail.com").build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());

        //when
        ResultActions result = mockMvc.perform(get("/api/employees/{id}", employeeId));


        //then

        result.andExpect(status().isNotFound()).andDo(print());
    }

    //Junit test to update employee //positive scenario
    @Test
    public void givenUpdateEmp_whenUpdateEmployee_thenReturnEmployeeObj() throws Exception{
        //given
       long employeeId=1L;
       Employee savedEmployee= Employee.builder().firstName("John").lastName("h").email("kener@gmail.com").build();
        Employee updateEmployee= Employee.builder().firstName("Jhjbghn").lastName("hnjb").email("kenmihbgber@gmail.com").build();
       given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.of(savedEmployee));
       given(employeeService.updateEmployee(any(Employee.class))).willAnswer((invocation -> invocation.getArgument(0)));

        //when
ResultActions r=mockMvc.perform(put("/api/employees/{id}", employeeId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateEmployee)));

        //then

        r.andExpect(status().isOk()).andDo(print())
                .andExpect(jsonPath("$.firstName",is(updateEmployee.getFirstName())))
                .andExpect(jsonPath("$.lastName",is(updateEmployee.getLastName()))).andExpect(jsonPath("$.email",is(updateEmployee.getEmail())));
    }

    //Junit test case for negative scenario for update employee
    @Test
    public void givenUpdateEmpp_whenUpdateEmployee_thenReturnEmployeeObj() throws Exception{
        //given
        long employeeId=1L;
        Employee savedEmployee= Employee.builder().firstName("John").lastName("h").email("kener@gmail.com").build();
        Employee updateEmployee= Employee.builder().firstName("Jhjbghn").lastName("hnjb").email("kenmihbgber@gmail.com").build();
        given(employeeService.getEmployeeById(employeeId)).willReturn(Optional.empty());
        given(employeeService.updateEmployee(any(Employee.class))).willAnswer((invocation -> invocation.getArgument(0)));

        //when
        ResultActions r=mockMvc.perform(put("/api/employees/{id}", employeeId).contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(updateEmployee)));

        //then

        r.andExpect(status().isNotFound()).andDo(print());
    }

    //junit test case to delete emp by id
    @Test
    public void givenEmpId_whenDeleteEmp_thenReturnNothing() throws Exception{
         //given
        long employeeId=1L;
         willDoNothing().given(employeeService).deleteEmployee(employeeId);

         //when

       ResultActions r= mockMvc.perform(delete("/api/employees/{id}",employeeId));


        //then

        r.andExpect(status().isOk()).andDo(print());


    }


}
