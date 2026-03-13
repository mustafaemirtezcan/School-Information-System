package org.sms.schoolmanagementsystem.dto;
import lombok.*;
import org.sms.schoolmanagementsystem.entity.Address;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.School;

import java.util.Date;
import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StudentDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String birthDate;
    private String email;
    private String phoneNumber;
    private String studentNumber;
    private Double gpa;
    private int achievedCredits;
    private String password;

    private School school;
    private Address address;
    private Set<Course> courses;
}
