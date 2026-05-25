package com.photobooking.config;

import com.photobooking.model.*;
import com.photobooking.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

// load sample data on app startup
@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final ClientRepository clientRepository;
    private final PhotographerRepository photographerRepository;
    private final PackageRepository packageRepository;
    private final BookingRepository bookingRepository;

    @Override
    public void run(String... args) throws Exception {
        // only load if database is empty
        if (clientRepository.count() == 0) {
            loadSampleData();
        }
    }

    private void loadSampleData() {
        // create 3 clients
        Client client1 = new Client();
        client1.setFirstName("Ravi");
        client1.setLastName("Silva");
        client1.setEmail("ravi.silva@example.com");
        client1.setPhone("0771234567");
        client1.setAddress("123 Main Street, Colombo");
        clientRepository.save(client1);

        Client client2 = new Client();
        client2.setFirstName("Priya");
        client2.setLastName("Gunawardena");
        client2.setEmail("priya.g@example.com");
        client2.setPhone("0772234567");
        client2.setAddress("456 Park Road, Kandy");
        clientRepository.save(client2);

        Client client3 = new Client();
        client3.setFirstName("Amara");
        client3.setLastName("Fernando");
        client3.setEmail("amara.f@example.com");
        client3.setPhone("0773234567");
        client3.setAddress("789 Beach Lane, Galle");
        clientRepository.save(client3);

        // create 3 photographers
        Photographer photo1 = new Photographer();
        photo1.setFirstName("Nimal");
        photo1.setLastName("Jayasundara");
        photo1.setEmail("nimal.j@example.com");
        photo1.setPhone("0774234567");
        photo1.setSpecialization("Wedding Photography");
        photo1.setYearsExperience(8);
        photo1.setAvailable(true);
        photographerRepository.save(photo1);

        Photographer photo2 = new Photographer();
        photo2.setFirstName("Chandra");
        photo2.setLastName("Wickramage");
        photo2.setEmail("chandra.w@example.com");
        photo2.setPhone("0775234567");
        photo2.setSpecialization("Corporate Events");
        photo2.setYearsExperience(5);
        photo2.setAvailable(true);
        photographerRepository.save(photo2);

        Photographer photo3 = new Photographer();
        photo3.setFirstName("Dinesh");
        photo3.setLastName("Kapoor");
        photo3.setEmail("dinesh.k@example.com");
        photo3.setPhone("0776234567");
        photo3.setSpecialization("Videography");
        photo3.setYearsExperience(6);
        photo3.setAvailable(true);
        photographerRepository.save(photo3);

        // create 2 photography packages
        PhotographyPackage pkg1 = new PhotographyPackage();
        pkg1.setPackageName("Classic Wedding");
        pkg1.setDescription("Full day coverage with album");
        pkg1.setPrice(25000.0);
        pkg1.setDurationHours(8);
        pkg1.setPackageType(PhotographyPackage.PackageType.PHOTOGRAPHY);
        pkg1.setIncludesVideography(false);
        pkg1.setIncludesEditing(true);
        pkg1.setMaxEditedPhotos(300);
        pkg1.setActive(true);
        packageRepository.save(pkg1);

        PhotographyPackage pkg2 = new PhotographyPackage();
        pkg2.setPackageName("Premium Wedding");
        pkg2.setDescription("Full day + pre-wedding session");
        pkg2.setPrice(45000.0);
        pkg2.setDurationHours(12);
        pkg2.setPackageType(PhotographyPackage.PackageType.BOTH);
        pkg2.setIncludesVideography(true);
        pkg2.setIncludesEditing(true);
        pkg2.setMaxEditedPhotos(500);
        pkg2.setActive(true);
        packageRepository.save(pkg2);

        // create 2 bookings
        Booking booking1 = new Booking();
        booking1.setClient(client1);
        booking1.setPhotographer(photo1);
        booking1.setPhotographyPackage(pkg1);
        booking1.setEventType("Wedding");
        booking1.setEventDate(LocalDate.now().plusMonths(2));
        booking1.setEventLocation("Colombo Grand Hall");
        booking1.setStatus(Booking.BookingStatus.PENDING);
        booking1.setTotalAmount(BigDecimal.valueOf(25000.0));
        booking1.setNotes("Request for outdoor shots");
        bookingRepository.save(booking1);

        Booking booking2 = new Booking();
        booking2.setClient(client2);
        booking2.setPhotographer(photo2);
        booking2.setPhotographyPackage(pkg2);
        booking2.setEventType("Corporate Event");
        booking2.setEventDate(LocalDate.now().plusMonths(1));
        booking2.setEventLocation("Hilton Hotel, Colombo");
        booking2.setStatus(Booking.BookingStatus.CONFIRMED);
        booking2.setTotalAmount(BigDecimal.valueOf(45000.0));
        booking2.setNotes("Company annual dinner");
        bookingRepository.save(booking2);
    }
}
