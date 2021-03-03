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
public class AppointmentDto {

    @NotBlank(message = "Name is required!")
    private String userName;

    @NotBlank(message = "User CarTrademark is required!")
    private Set<String> carTrademarks;

    @NotBlank(message = "Company name is required!")
    private String companyName;

    @NotBlank(message = "Date time name is required!")
    private String dateTime;

}
