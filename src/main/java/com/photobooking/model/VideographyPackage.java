package com.photobooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

@Entity
@Table(name = "videography_packages")
@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
public class VideographyPackage extends BasePackage {

    @Min(value = 1, message = "Video duration must be at least 1 minute")
    @Column(name = "video_duration_minutes")
    private int videoDurationMinutes;

    @Column(name = "drone_footage_included", nullable = false)
    private boolean droneFootageIncluded;

    @Override
    public String getPackageCategory() {
        return "VIDEOGRAPHY";
    }

    @Override
    public String getFormattedPrice() {
        return String.format("Rs. %.2f (Videography)", getPrice());
    }
}
