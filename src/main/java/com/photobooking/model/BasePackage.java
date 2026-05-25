package com.photobooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

// Abstraction — base class for package types
@MappedSuperclass
@Getter
@Setter
@NoArgsConstructor
public abstract class BasePackage {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Package name is required")
    @Size(max = 150)
    @Column(name = "name", nullable = false, length = 150)
    private String packageName;

    @Size(max = 1000)
    @Column(name = "description", columnDefinition = "TEXT")
    private String description;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    @Column(name = "price", nullable = false)
    private Double price;

    @Min(value = 1, message = "Duration must be at least 1 hour")
    @Column(name = "duration_hours", nullable = false)
    private int durationHours;

    // Returns a label like "PHOTOGRAPHY" or "VIDEOGRAPHY" — distinct from the
    // getPackageType() Lombok getter for PhotographyPackage's packageType enum field
    public abstract String getPackageCategory();

    public abstract String getFormattedPrice();

    // Bridge methods — template uses ${pkg.name}; field was renamed to packageName during refactoring
    public String getName() { return packageName; }
    public void setName(String name) { this.packageName = name; }
}
