package com.photobooking.controller;

import com.photobooking.model.PhotographyPackage;
import com.photobooking.service.PackageService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/packages")
@RequiredArgsConstructor
public class PackageController {

    private final PackageService packageService;

    // show all packages, with optional search
    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("packages", packageService.search(search));
        model.addAttribute("search", search);
        // pass package types so the form can show a dropdown
        model.addAttribute("packageTypes", PhotographyPackage.PackageType.values());
        return "package/list";
    }

    // show one package by id
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        // check if id exists
        PhotographyPackage pkg = packageService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Package not found: " + id));
        model.addAttribute("pkg", pkg);
        return "package/view";
    }

    // show blank form for adding a package
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("pkg", new PhotographyPackage());
        model.addAttribute("packageTypes", PhotographyPackage.PackageType.values());
        return "package/form";
    }

    // save the new package
    @PostMapping("/new")
    public String create(@Valid @ModelAttribute("pkg") PhotographyPackage pkg,
                         BindingResult result,
                         Model model,
                         RedirectAttributes flash) {
        // go back to form if validation failed
        if (result.hasErrors()) {
            model.addAttribute("packageTypes", PhotographyPackage.PackageType.values());
            return "package/form";
        }
        packageService.save(pkg);
        flash.addFlashAttribute("successMsg", "Package created successfully!");
        return "redirect:/packages";
    }

    // show edit form with existing package data
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        PhotographyPackage pkg = packageService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Package not found: " + id));
        model.addAttribute("pkg", pkg);
        model.addAttribute("packageTypes", PhotographyPackage.PackageType.values());
        return "package/form";
    }

    // update the package in the database
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("pkg") PhotographyPackage pkg,
                         BindingResult result,
                         Model model,
                         RedirectAttributes flash) {
        if (result.hasErrors()) {
            model.addAttribute("packageTypes", PhotographyPackage.PackageType.values());
            return "package/form";
        }
        packageService.update(id, pkg);
        flash.addFlashAttribute("successMsg", "Package updated successfully!");
        return "redirect:/packages";
    }

    // delete the package by id
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        packageService.deleteById(id);
        flash.addFlashAttribute("successMsg", "Package deleted.");
        return "redirect:/packages";
    }
}
