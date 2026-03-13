package org.sms.schoolmanagementsystem.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.Set;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class SchoolUpdateDto {

    @NotEmpty(message = "School name cannot be empty")
    private String name;
    @NotNull(message = "Address ID cannot be null")
    private int addressId;
}
