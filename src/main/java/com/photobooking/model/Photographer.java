package com.photobooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "photographers")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "bookings")
@EqualsAndHashCode(callSuper = false, exclude = "bookings")
public class Photographer extends Person {

    @NotBlank(message = "First name is required")
    @Size(max = 100)
    @Column(name = "first_name", nullable = false, length = 100)
    private String firstName;

    @NotBlank(message = "Last name is required")
    @Size(max = 100)
    @Column(name = "last_name", nullable = false, length = 100)
    private String lastName;

    @NotBlank(message = "Specialization is required")
    @Size(max = 150)
    @Column(name = "specialization", nullable = false, length = 150)
    private String specialization;

    @Size(max = 1000)
    @Column(name = "bio", columnDefinition = "TEXT")
    private String bio;

    @DecimalMin(value = "0.0", message = "Years of experience cannot be negative")
    @Column(name = "years_experience")
    private Integer yearsExperience;

    @Column(name = "available", nullable = false)
    private Boolean available = true;

    @OneToMany(mappedBy = "photographer", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();

    public String getFullName() {
        return firstName + " " + lastName;
    }

    @Override
    public String getRole() {
        return "PHOTOGRAPHER";
    }
}
