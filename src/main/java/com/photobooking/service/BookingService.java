package com.photobooking.service;

import com.photobooking.model.Booking;
import com.photobooking.model.Client;
import com.photobooking.model.Photographer;
import com.photobooking.model.PhotographyPackage;
import com.photobooking.repository.BookingRepository;
import com.photobooking.repository.ClientRepository;
import com.photobooking.repository.PackageRepository;
import com.photobooking.repository.PhotographerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class BookingService {

    private final BookingRepository bookingRepository;
    private final ClientRepository clientRepository;
    private final PhotographerRepository photographerRepository;
    private final PackageRepository packageRepository;

    // ── Read ─────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Booking> findAll() {
        return bookingRepository.findAllOrderByCreatedAtDesc();
    }

    @Transactional(readOnly = true)
    public Optional<Booking> findById(Long id) {
        return bookingRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Booking> findByStatus(Booking.BookingStatus status) {
        return bookingRepository.findByStatus(status);
    }

    @Transactional(readOnly = true)
    public List<Booking> findUpcoming() {
        return bookingRepository.findUpcomingBookings(LocalDate.now());
    }

    @Transactional(readOnly = true)
    public List<Booking> findByClient(Long clientId) {
        return bookingRepository.findByClientId(clientId);
    }

    @Transactional(readOnly = true)
    public List<Booking> findByPhotographer(Long photographerId) {
        return bookingRepository.findByPhotographerId(photographerId);
    }

    // ── Write ────────────────────────────────────────────────
    public Booking createBooking(Long clientId, Long photographerId, Long packageId,
                                 Booking booking) {
        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new IllegalArgumentException("Photographer not found"));
        PhotographyPackage pkg = packageRepository.findById(packageId)
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));

        booking.setClient(client);
        booking.setPhotographer(photographer);
        booking.setPhotographyPackage(pkg);
        booking.setTotalAmount(pkg.getPrice() != null ? java.math.BigDecimal.valueOf(pkg.getPrice()) : null);
        booking.setStatus(Booking.BookingStatus.PENDING);

        return bookingRepository.save(booking);
    }

    public Booking updateStatus(Long id, Booking.BookingStatus newStatus) {
        Booking booking = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + id));
        booking.setStatus(newStatus);
        return bookingRepository.save(booking);
    }

    public Booking update(Long id, Long clientId, Long photographerId, Long packageId,
                          Booking updated) {
        Booking existing = bookingRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + id));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new IllegalArgumentException("Client not found"));
        Photographer photographer = photographerRepository.findById(photographerId)
                .orElseThrow(() -> new IllegalArgumentException("Photographer not found"));
        PhotographyPackage pkg = packageRepository.findById(packageId)
                .orElseThrow(() -> new IllegalArgumentException("Package not found"));

        existing.setClient(client);
        existing.setPhotographer(photographer);
        existing.setPhotographyPackage(pkg);
        existing.setEventType(updated.getEventType());
        existing.setEventDate(updated.getEventDate());
        existing.setEventStartTime(updated.getEventStartTime());
        existing.setEventLocation(updated.getEventLocation());
        existing.setStatus(updated.getStatus());
        existing.setTotalAmount(pkg.getPrice() != null ? java.math.BigDecimal.valueOf(pkg.getPrice()) : null);
        existing.setNotes(updated.getNotes());

        return bookingRepository.save(existing);
    }

    public void deleteById(Long id) {
        bookingRepository.deleteById(id);
    }

    // ── Stats ────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public long count() {
        return bookingRepository.count();
    }

    @Transactional(readOnly = true)
    public long countByStatus(Booking.BookingStatus status) {
        return bookingRepository.countByStatus(status);
    }
}
