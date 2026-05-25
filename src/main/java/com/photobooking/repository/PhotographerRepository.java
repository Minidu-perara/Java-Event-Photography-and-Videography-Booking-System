package com.photobooking.repository;

import com.photobooking.model.Photographer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PhotographerRepository extends JpaRepository<Photographer, Long> {

    Optional<Photographer> findByEmail(String email);

    boolean existsByEmail(String email);

    List<Photographer> findByAvailableTrue();

    List<Photographer> findBySpecializationContainingIgnoreCase(String specialization);

    @Query("SELECT p FROM Photographer p WHERE LOWER(p.firstName) LIKE LOWER(CONCAT('%', :name, '%')) " +
           "OR LOWER(p.lastName) LIKE LOWER(CONCAT('%', :name, '%'))")
    List<Photographer> searchByName(String name);
}
