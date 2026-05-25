package com.photobooking.controller;

import com.photobooking.model.Photographer;
import com.photobooking.service.PhotographerService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/photographers")
@RequiredArgsConstructor
public class PhotographerController {

    private final PhotographerService photographerService;

    // get all photographers from database, with optional search
    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("photographers", photographerService.search(search));
        model.addAttribute("search", search);
        return "photographer/list";
    }

    // show details for one photographer
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        // check if id exists
        Photographer photographer = photographerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Photographer not found: " + id));
        model.addAttribute("photographer", photographer);
        return "photographer/view";
    }

    // show blank form for new photographer
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("photographer", new Photographer());
        return "photographer/form";
    }

    // save new photographer
    @PostMapping("/new")
    public String create(@Valid @ModelAttribute Photographer photographer,
                         BindingResult result,
                         RedirectAttributes flash) {
        // return to form if there are errors
        if (result.hasErrors()) {
            return "photographer/form";
        }
        photographerService.save(photographer);
        flash.addFlashAttribute("successMsg", "Photographer added successfully!");
        return "redirect:/photographers";
    }

    // show form with existing data for editing
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        Photographer photographer = photographerService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Photographer not found: " + id));
        model.addAttribute("photographer", photographer);
        return "photographer/form";
    }

    // update photographer in the database
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute Photographer photographer,
                         BindingResult result,
                         RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "photographer/form";
        }
        photographerService.update(id, photographer);
        flash.addFlashAttribute("successMsg", "Photographer updated successfully!");
        return "redirect:/photographers";
    }

    // delete photographer by id
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        photographerService.deleteById(id);
        flash.addFlashAttribute("successMsg", "Photographer deleted.");
        return "redirect:/photographers";
    }
}
