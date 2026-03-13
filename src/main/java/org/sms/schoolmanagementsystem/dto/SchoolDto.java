package org.sms.schoolmanagementsystem.dto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.sms.schoolmanagementsystem.entity.Address;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.Lecturer;
import org.sms.schoolmanagementsystem.entity.Student;

import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchoolDto {

    private long id;
    private String name;
    private Address address;
    private Set<Lecturer> lecturers;
    private Set<Student> students;
    private Set<Course> courses;
}
