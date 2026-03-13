package org.sms.schoolmanagementsystem.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String courseCode;
    private int credits;

    @ManyToOne
    @JsonBackReference
    private School school;

    @ManyToMany(mappedBy = "courses")
    @JsonBackReference
    private Set<Student> students;

    @ManyToMany(mappedBy = "courses")
    @JsonBackReference
    private Set<Lecturer> lecturers;
}
