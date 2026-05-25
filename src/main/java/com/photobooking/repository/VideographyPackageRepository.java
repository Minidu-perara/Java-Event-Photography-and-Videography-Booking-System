package com.photobooking.repository;

import com.photobooking.model.VideographyPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface VideographyPackageRepository extends JpaRepository<VideographyPackage, Long> {

    List<VideographyPackage> findByPackageNameContainingIgnoreCase(String name);
}
