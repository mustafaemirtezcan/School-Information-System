package org.sms.schoolmanagementsystem.dto;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class StudentUpdateDto {

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;


    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "Birth date must be in the format YYYY-MM-DD")
    private String birthDate;

    private String phoneNumber;
    private String studentNumber;
    private Double gpa;
    private int achievedCredits;
    private String password;

    @NotNull(message = "Address ID cannot be null")
    private int addressId;
    @NotNull(message = "School ID cannot be null")
    private long schoolId;

    @Size(max = 30, message = "Maximum number of courses is 30")
    private Set<Integer> courseIds;
}
