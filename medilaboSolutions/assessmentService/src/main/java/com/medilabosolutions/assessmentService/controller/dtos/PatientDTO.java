package com.medilabosolutions.assessmentService.controller.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medilabosolutions.assessmentService.controller.dtos.enums.Gender;
import lombok.Setter;

import java.time.LocalDate;


@Setter
public class PatientDTO {

    private Long id;
    private String firstname;
    private String lastname;
    @JsonFormat(pattern="yyyy-MM-dd")
    private LocalDate birthdate;
    private Gender gender;
    private String address;
    private String phone;

    public PatientDTO(Long id, String firstname, String lastname, LocalDate birthdate, Gender gender, String address, String phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }

    public PatientDTO() {
    }

    public Long getId() {
        return id;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public LocalDate getBirthdate() {
        return birthdate;
    }

    public Gender getGender() {
        return gender;
    }
}
