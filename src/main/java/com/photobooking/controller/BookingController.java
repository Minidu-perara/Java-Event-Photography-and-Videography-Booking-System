package com.photobooking.controller;

import com.photobooking.model.Booking;
import com.photobooking.service.BookingService;
import com.photobooking.service.ClientService;
import com.photobooking.service.PackageService;
import com.photobooking.service.PhotographerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingController {

    private final BookingService bookingService;
    private final ClientService clientService;
    private final PhotographerService photographerService;
    private final PackageService packageService;

    // show list of all bookings, filter by status if given
    @GetMapping
    public String list(@RequestParam(required = false) String status, Model model) {
        if (status != null && !status.isBlank()) {
            try {
                // convert the string to enum value
                Booking.BookingStatus bs = Booking.BookingStatus.valueOf(status.toUpperCase());
                model.addAttribute("bookings", bookingService.findByStatus(bs));
                model.addAttribute("currentStatus", bs);
            } catch (IllegalArgumentException e) {
                // if status is invalid, just show all
                model.addAttribute("bookings", bookingService.findAll());
            }
        } else {
            // no filter, show everything
            model.addAttribute("bookings", bookingService.findAll());
        }
        model.addAttribute("statuses", Booking.BookingStatus.values());
        return "booking/list";
    }

    // show one booking by id
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        // check if booking exists
        Booking booking = bookingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + id));
        model.addAttribute("booking", booking);
        model.addAttribute("statuses", Booking.BookingStatus.values());
        return "booking/view";
    }

    // show empty booking form
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("booking", new Booking());
        addFormData(model);
        return "booking/form";
    }

    // save the new booking
    @PostMapping("/new")
    public String create(@RequestParam(required = false) Long clientId,
                         @RequestParam(required = false) Long photographerId,
                         @RequestParam(required = false) Long packageId,
                         @Valid @ModelAttribute Booking booking,
                         BindingResult result,
                         Model model,
                         RedirectAttributes flash) {

        // make sure the user selected all 3 dropdowns
        if (clientId == null || photographerId == null || packageId == null) {
            flash.addFlashAttribute("errorMsg", "Please select a client, photographer, and package.");
            return "redirect:/bookings/new";
        }

        // check for validation errors on the form fields
        if (result.hasErrors()) {
            addFormData(model);
            return "booking/form";
        }

        // save the booking to the database
        bookingService.createBooking(clientId, photographerId, packageId, booking);
        flash.addFlashAttribute("successMsg", "Booking created successfully!");
        return "redirect:/bookings";
    }

    // show edit form pre-filled with existing booking data
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Booking booking = bookingService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + id));
        model.addAttribute("booking", booking);
        addFormData(model);
        return "booking/form";
    }

    // update the existing booking
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @RequestParam(required = false) Long clientId,
                         @RequestParam(required = false) Long photographerId,
                         @RequestParam(required = false) Long packageId,
                         @Valid @ModelAttribute Booking booking,
                         BindingResult result,
                         Model model,
                         RedirectAttributes flash) {

        // check dropdowns are filled
        if (clientId == null || photographerId == null || packageId == null) {
            flash.addFlashAttribute("errorMsg", "Please select a client, photographer, and package.");
            return "redirect:/bookings/" + id + "/edit";
        }

        if (result.hasErrors()) {
            addFormData(model);
            return "booking/form";
        }

        // update in database
        bookingService.update(id, clientId, photographerId, packageId, booking);
        flash.addFlashAttribute("successMsg", "Booking updated successfully!");
        return "redirect:/bookings";
    }

    // update just the status field of a booking
    @PostMapping("/{id}/status")
    public String updateStatus(@PathVariable Long id,
                               @RequestParam String status,
                               RedirectAttributes flash) {
        Booking.BookingStatus bs = Booking.BookingStatus.valueOf(status);
        bookingService.updateStatus(id, bs);
        flash.addFlashAttribute("successMsg", "Status updated to " + bs + ".");
        return "redirect:/bookings/" + id;
    }

    // delete booking by id
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        bookingService.deleteById(id);
        flash.addFlashAttribute("successMsg", "Booking deleted.");
        return "redirect:/bookings";
    }

    // helper method to put clients, photographers, packages into the model
    private void addFormData(Model model) {
        model.addAttribute("clients", clientService.findAll());
        model.addAttribute("photographers", photographerService.findAll());
        model.addAttribute("packages", packageService.findActive());
        model.addAttribute("statuses", Booking.BookingStatus.values());
    }
}
