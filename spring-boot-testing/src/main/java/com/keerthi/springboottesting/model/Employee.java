package com.keerthi.springboottesting.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder// to create obj of employee and set values
@Entity
@Table(name= "employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Employee_Id")
    private long id;

    @Column(name = "First_Name",nullable = false)
    private String firstName;

    @Column(name = "Last_Name",nullable = false)
    private String lastName;

    private String email;

}
