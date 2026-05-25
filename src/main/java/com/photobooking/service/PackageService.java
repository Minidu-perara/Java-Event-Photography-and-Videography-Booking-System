package com.photobooking.service;

import com.photobooking.model.BasePackage;
import com.photobooking.model.PhotographyPackage;
import com.photobooking.model.VideographyPackage;
import com.photobooking.repository.PackageRepository;
import com.photobooking.repository.VideographyPackageRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class PackageService {

    private final PackageRepository packageRepository;
    private final VideographyPackageRepository videographyPackageRepository;

    @Transactional(readOnly = true)
    public List<PhotographyPackage> findAll() {
        return packageRepository.findAll();
    }

    @Transactional(readOnly = true)
    public List<PhotographyPackage> findActive() {
        return packageRepository.findByActiveTrueOrderByPriceAsc();
    }

    @Transactional(readOnly = true)
    public Optional<PhotographyPackage> findById(Long id) {
        return packageRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<PhotographyPackage> search(String name) {
        if (name == null || name.isBlank()) return findAll();
        return packageRepository.findByPackageNameContainingIgnoreCase(name.trim());
    }

    // Polymorphism — iterating mixed list, calling getPackageCategory() on each
    @Transactional(readOnly = true)
    public List<BasePackage> getAllPackages() {
        List<BasePackage> all = new ArrayList<>();
        all.addAll(packageRepository.findAll());
        all.addAll(videographyPackageRepository.findAll());
        return all;
    }

    public PhotographyPackage save(PhotographyPackage pkg) {
        return packageRepository.save(pkg);
    }

    public VideographyPackage save(VideographyPackage pkg) {
        return videographyPackageRepository.save(pkg);
    }

    public PhotographyPackage update(Long id, PhotographyPackage updated) {
        PhotographyPackage existing = packageRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Package not found: " + id));
        existing.setPackageName(updated.getPackageName());
        existing.setDescription(updated.getDescription());
        existing.setPrice(updated.getPrice());
        existing.setDurationHours(updated.getDurationHours());
        existing.setPackageType(updated.getPackageType());
        existing.setIncludesVideography(updated.getIncludesVideography());
        existing.setIncludesEditing(updated.getIncludesEditing());
        existing.setMaxEditedPhotos(updated.getMaxEditedPhotos());
        existing.setActive(updated.getActive());
        return packageRepository.save(existing);
    }

    public void deleteById(Long id) {
        packageRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public long count() {
        return packageRepository.count();
    }
}
