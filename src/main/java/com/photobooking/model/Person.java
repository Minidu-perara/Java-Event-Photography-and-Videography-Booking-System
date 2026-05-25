package com.photobooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

// Abstraction + Encapsulation — base class for all persons
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @NotBlank(message = "Email is required")
    @Email(message = "Invalid email address")
    @Column(unique = true, nullable = false, length = 150)
    private String email;

    @NotBlank(message = "Phone number is required")
    @Column(name = "phone", nullable = false, length = 30)
    private String phone;

    public abstract String getRole();
}
