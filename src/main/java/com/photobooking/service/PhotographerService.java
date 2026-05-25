package com.photobooking.service;

import com.photobooking.model.Photographer;
import com.photobooking.repository.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PhotographerService {

    private final PhotographerRepository photographerRepository;

    @Transactional(readOnly = true)
    public List<Photographer> findAll() {
        return photographerRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Photographer> findById(Long id) {
        return photographerRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Photographer> findAvailable() {
        return photographerRepository.findByAvailableTrue();
    }

    @Transactional(readOnly = true)
    public List<Photographer> search(String name) {
        if (name == null || name.isBlank()) return findAll();
        return photographerRepository.searchByName(name.trim());
    }

    public Photographer save(Photographer photographer) {
        return photographerRepository.save(photographer);
    }

    public Photographer update(Long id, Photographer updated) {
        Photographer existing = photographerRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Photographer not found: " + id));
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setSpecialization(updated.getSpecialization());
        existing.setBio(updated.getBio());
        existing.setYearsExperience(updated.getYearsExperience());
        existing.setAvailable(updated.getAvailable());
        return photographerRepository.save(existing);
    }

    public void deleteById(Long id) {
        photographerRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return photographerRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public long count() {
        return photographerRepository.count();
    }
}
