package org.sms.schoolmanagementsystem.dto;
import lombok.*;
import org.sms.schoolmanagementsystem.entity.Address;
import org.sms.schoolmanagementsystem.entity.Course;
import org.sms.schoolmanagementsystem.entity.School;


import java.util.Set;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class LecturerDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private String branch;
    private String password;
    private String lecturerNumber;

    private School school;
    private Address address;
    private Set<Course> courses;
}
