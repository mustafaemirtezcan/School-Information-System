package org.sms.schoolmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
public class School {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String name;

    @OneToOne
    private Address address;

    @OneToMany
    @JsonManagedReference
    private Set<Lecturer> lecturers = new HashSet<>();
    @OneToMany
    @JsonManagedReference
    private Set<Student> students = new HashSet<>();
    @OneToMany
    @JsonManagedReference
    private Set<Course> courses = new HashSet<>();

}
