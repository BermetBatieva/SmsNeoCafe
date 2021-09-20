package com.example.SmsNeoCafe.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Entity
@Table(	name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name ="first_name")
    private String firstName;

    @Column(name ="last_name")
    private String lastName;

    @Column(name ="b_date")
    private Date bDate;

    @Column(name ="phone_number")
    private String phoneNumber;


    @Enumerated(EnumType.STRING)
    private ERole role;


    private long bonus;


    @Column(name = "activation_code")
    private String activationCode;

    @Column(name = "is_active")
    private boolean active;

}

