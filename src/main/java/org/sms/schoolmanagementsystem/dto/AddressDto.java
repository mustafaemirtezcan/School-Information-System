package org.sms.schoolmanagementsystem.dto;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AddressDto {
    private int id;

    private int zipCode;
    private String city;
    private String definition;
}
