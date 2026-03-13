package org.sms.schoolmanagementsystem.dto;
import lombok.*;
import org.sms.schoolmanagementsystem.entity.Lecturer;
import org.sms.schoolmanagementsystem.entity.School;
import org.sms.schoolmanagementsystem.entity.Student;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseDto {
    private int id;
    private String name;
    private String courseCode;
    private int credits;
    private School school;
    private Set<Lecturer> lecturers;
    private Set<Student> students;

}

