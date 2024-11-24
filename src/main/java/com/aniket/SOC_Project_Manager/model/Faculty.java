package com.aniket.SOC_Project_Manager.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;



@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "Faculty")
@Entity
public class Faculty {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long user_id;

    @NotBlank
    private String name;

    @Column(nullable = false, unique = true)
    @Email
    private String email;

    @Column(nullable = false)
    @NotBlank
    private String password;


    private String address;
}
