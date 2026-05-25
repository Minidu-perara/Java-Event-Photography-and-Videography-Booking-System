package com.photobooking.repository;

import com.photobooking.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    List<Booking> findByClientId(Long clientId);

    List<Booking> findByPhotographerId(Long photographerId);

    List<Booking> findByStatus(Booking.BookingStatus status);

    List<Booking> findByEventDateBetween(LocalDate start, LocalDate end);

    List<Booking> findByPhotographerIdAndEventDate(Long photographerId, LocalDate date);

    @Query("SELECT COUNT(b) FROM Booking b WHERE b.status = :status")
    long countByStatus(Booking.BookingStatus status);

    @Query("SELECT b FROM Booking b ORDER BY b.createdAt DESC")
    List<Booking> findAllOrderByCreatedAtDesc();

    @Query("SELECT b FROM Booking b WHERE b.eventDate >= :today ORDER BY b.eventDate ASC")
    List<Booking> findUpcomingBookings(LocalDate today);
}
