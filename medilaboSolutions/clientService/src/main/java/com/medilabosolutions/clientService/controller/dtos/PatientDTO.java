package com.medilabosolutions.clientService.controller.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medilabosolutions.clientService.controller.dtos.enums.Gender;
import jakarta.validation.constraints.*;
import lombok.*;

import java.time.LocalDate;

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
    private LocalDate birthdate;
    private Gender gender;
    private String address;
   @Pattern(regexp = "^|[0-9].{9,}$", message = "phone must contain 10 digits")
    private String phone;

   @JsonIgnore
    public String getPhoneFormat() {
        return phone.substring(0,3) + "-" + phone.substring(3,6) + "-" + phone.substring(6);
    }
}
