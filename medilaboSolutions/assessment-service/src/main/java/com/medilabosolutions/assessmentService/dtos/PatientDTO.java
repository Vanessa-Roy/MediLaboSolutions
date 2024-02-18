package com.medilabosolutions.assessmentService.dtos;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.medilabosolutions.assessmentService.enums.Gender;
import lombok.Setter;

import java.time.LocalDate;


/**
 * Represents a dto used to read a real patient.
 */
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

    /**
     * Instantiates a new Patient dto.
     *
     * @param id        the id
     * @param firstname the firstname
     * @param lastname  the lastname
     * @param birthdate the birthdate
     * @param gender    the gender
     * @param address   the address
     * @param phone     the phone
     */
    public PatientDTO(Long id, String firstname, String lastname, LocalDate birthdate, Gender gender, String address, String phone) {
        this.id = id;
        this.firstname = firstname;
        this.lastname = lastname;
        this.birthdate = birthdate;
        this.gender = gender;
        this.address = address;
        this.phone = phone;
    }

    /**
     * Instantiates a new Patient dto.
     */
    public PatientDTO() {
    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * Gets firstname.
     *
     * @return the firstname
     */
    public String getFirstname() {
        return firstname;
    }

    /**
     * Gets lastname.
     *
     * @return the lastname
     */
    public String getLastname() {
        return lastname;
    }

    /**
     * Gets birthdate.
     *
     * @return the birthdate
     */
    public LocalDate getBirthdate() {
        return birthdate;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public Gender getGender() {
        return gender;
    }
}
