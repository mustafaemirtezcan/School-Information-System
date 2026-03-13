package org.sms.schoolmanagementsystem.dto;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;


import java.util.Set;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class LecturerUpdateDto {

    @NotEmpty(message = "First name cannot be empty")
    private String firstName;
    @NotEmpty(message = "Last name cannot be empty")
    private String lastName;
    @Email(message = "Email should be valid")
    private String email;

    private String lecturerNumber;
    private String phoneNumber;
    private String branch;
    private String password;

    @NotNull(message = "Address ID cannot be null")
    private int addressId;
    @NotNull(message = "School ID cannot be null")
    private long schoolId;

    @Size(max = 30, message = "Maximum number of courses is 30")
    private Set<Integer> courseIds;
}
