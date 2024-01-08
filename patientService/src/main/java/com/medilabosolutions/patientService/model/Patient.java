package com.medilabosolutions.patientService.model;

import com.medilabosolutions.patientService.model.enums.Gender;
import jakarta.persistence.*;

import java.time.LocalDate;

@Entity
@Table(name = "patients")
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String firstname;
    @Column(nullable = false)
    private String lastname;
    @Column(nullable = false)
    private LocalDate birthdate;
    @Column(nullable = false)
    private Gender gender;
    @Column(nullable = false)
    private String address;
    @Column(nullable = false)
    private Long phone;

}
