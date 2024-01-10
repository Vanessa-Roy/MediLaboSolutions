package com.medilabosolutions.patientService.dtos;

import com.medilabosolutions.patientService.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;

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
    private Long phone;
}
