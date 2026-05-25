package com.photobooking.controller;

import com.photobooking.model.Booking;
import com.photobooking.service.BookingService;
import com.photobooking.service.ClientService;
import com.photobooking.service.PackageService;
import com.photobooking.service.PhotographerService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final ClientService clientService;
    private final PhotographerService photographerService;
    private final PackageService packageService;
    private final BookingService bookingService;

    // show the dashboard page
    @GetMapping("/")
    public String index(Model model) {
        // get counts for the 4 stat boxes
        model.addAttribute("totalClients", clientService.count());
        model.addAttribute("totalPhotographers", photographerService.count());
        model.addAttribute("totalPackages", packageService.count());
        model.addAttribute("totalBookings", bookingService.count());

        // get pending and confirmed counts for the summary table
        model.addAttribute("pendingBookings", bookingService.countByStatus(Booking.BookingStatus.PENDING));
        model.addAttribute("confirmedBookings", bookingService.countByStatus(Booking.BookingStatus.CONFIRMED));

        // get upcoming bookings to show in the table at the bottom
        model.addAttribute("upcomingBookings", bookingService.findUpcoming());

        return "index";
    }
}
