package com.medilabosolutions.patientService.controller.dtos;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.medilabosolutions.patientService.model.enums.Gender;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDate;

/**
 * Represents a dto used to read or update a real patient.
 */
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
}
