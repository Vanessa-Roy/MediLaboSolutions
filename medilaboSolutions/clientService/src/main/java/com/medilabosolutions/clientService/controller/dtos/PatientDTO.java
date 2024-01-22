package com.medilabosolutions.clientService.controller.dtos;

import com.medilabosolutions.clientService.controller.dtos.enums.Gender;
import lombok.*;

import java.time.LocalDate;

@Getter
@Setter
public class PatientDTO {
    private Long id;
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private Gender gender;
    private String address;
    private String phone;

    public String getPhoneFormat() {
        return phone.substring(0,3) + "-" + phone.substring(3,6) + "-" + phone.substring(6);
    }
}
