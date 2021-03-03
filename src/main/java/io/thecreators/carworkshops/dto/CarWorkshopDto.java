package io.thecreators.carworkshops.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.Accessors;

import javax.validation.constraints.NotBlank;
import java.util.Set;

@Getter
@Setter
@Accessors(chain = true)
@NoArgsConstructor
public class CarWorkshopDto {

    @NotBlank(message = "Сompany name is required!")
    private String companyName;

    @NotBlank(message = "Car trademarks is required!")
    private Set<String> carTrademarks;

    @NotBlank(message = "City is required!")
    private String city;

    @NotBlank(message = "Postal code is required!")
    private String postalCode;

    @NotBlank(message = "Country is required!")
    private String country;

}
