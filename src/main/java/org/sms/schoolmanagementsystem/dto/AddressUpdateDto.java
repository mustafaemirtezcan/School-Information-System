package org.sms.schoolmanagementsystem.dto;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressUpdateDto {

    private int zipCode;
    @NotEmpty(message = "City cannot be empty")
    private String city;
    private String definition;
}
