package com.photobooking.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "packages")
@Getter
@Setter
@NoArgsConstructor
@ToString(exclude = "bookings")
@EqualsAndHashCode(callSuper = false, exclude = "bookings")
public class PhotographyPackage extends BasePackage {

    @Enumerated(EnumType.STRING)
    @Column(name = "package_type", nullable = false, length = 50)
    private PackageType packageType = PackageType.PHOTOGRAPHY;

    @Column(name = "includes_videography", nullable = false)
    private Boolean includesVideography = false;

    @Column(name = "includes_editing", nullable = false)
    private Boolean includesEditing = true;

    @Column(name = "max_edited_photos")
    private Integer maxEditedPhotos;

    @Column(name = "active", nullable = false)
    private Boolean active = true;

    @OneToMany(mappedBy = "photographyPackage", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Booking> bookings = new ArrayList<>();

    @Override
    public String getPackageCategory() {
        return "PHOTOGRAPHY";
    }

    @Override
    public String getFormattedPrice() {
        return String.format("Rs. %.2f (Photography)", getPrice());
    }

    public enum PackageType {
        PHOTOGRAPHY,
        VIDEOGRAPHY,
        BOTH
    }
}
