package org.sms.schoolmanagementsystem.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import java.util.List;



@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CourseUpdateDto {

    @NotEmpty(message = "Course name cannot be null")
    private String name;
    @NotEmpty(message = "Course code cannot be null")
    private String courseCode;
    @NotNull(message = "Credits cannot be null")
    private int credits;

    @NotNull(message = "School ID cannot be null")
    private long schoolId;
    private List<Long> students;
    private List<Long> lecturers;
}
