package io.thecreators.carworkshops.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class UserDto {

    @NotBlank(message = "Name is required!")
    private String userName;

    @NotBlank(message = "Email is required!")
    private String email;

    @NotBlank(message = "City is required!")
    private String city;

    @NotBlank(message = "Postal code is required!")
    private String postalCode;

    @NotBlank(message = "Country is required!")
    private String country;

}
