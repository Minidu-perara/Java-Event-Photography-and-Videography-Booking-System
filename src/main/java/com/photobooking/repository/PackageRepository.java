package com.photobooking.repository;

import com.photobooking.model.PhotographyPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PackageRepository extends JpaRepository<PhotographyPackage, Long> {

    List<PhotographyPackage> findByActiveTrue();

    List<PhotographyPackage> findByPackageType(PhotographyPackage.PackageType packageType);

    List<PhotographyPackage> findByActiveTrueOrderByPriceAsc();

    List<PhotographyPackage> findByPackageNameContainingIgnoreCase(String packageName);
}
