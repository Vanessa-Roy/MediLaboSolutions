package com.medilabosolutions.patientService.dto;

import com.medilabosolutions.patientService.model.enums.Gender;
import lombok.Data;

import java.time.LocalDate;

@Data
public class PatientDTO {
    private String firstname;
    private String lastname;
    private LocalDate birthdate;
    private Gender gender;
    private String address;
    private Long phone;
}
