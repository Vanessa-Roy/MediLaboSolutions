package com.medilabosolutions.clientService.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medilabosolutions.clientService.controller.dtos.enums.Gender;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

/**
 * Represents a dto used to read or update a real patient.
 */
@Getter
@Setter
public class PatientDTO {
    private Long id;
    @Pattern(regexp = "^[A-Za-z].*$", message = "firstname must contain letters")
    @NotBlank(message = "firstname is mandatory")
    private String firstname;
    @Pattern(regexp = "^[A-Za-z].*$", message = "lastname must contain letters")
    @NotBlank(message = "lastname is mandatory")
    private String lastname;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthdate;
    private Gender gender;
    private String address;
   @Pattern(regexp = "^|[0-9].{9,}$", message = "phone must contain 10 digits")
    private String phone;

    /**
     * Gets phone format.
     *
     * @return the phone format
     */
    @JsonIgnore
    public String getPhoneFormat() {
        return phone.substring(0,3) + "-" + phone.substring(3,6) + "-" + phone.substring(6);
    }

}
