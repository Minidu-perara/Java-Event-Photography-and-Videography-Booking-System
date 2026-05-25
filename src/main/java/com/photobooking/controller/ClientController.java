package com.photobooking.controller;

import com.photobooking.model.Client;
import com.photobooking.service.ClientService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/clients")
@RequiredArgsConstructor
public class ClientController {

    private final ClientService clientService;

    // show list of all clients, with optional search
    @GetMapping
    public String list(@RequestParam(required = false) String search, Model model) {
        model.addAttribute("clients", clientService.search(search));
        model.addAttribute("search", search);
        return "client/list";
    }

    // show a single client by id
    @GetMapping("/{id}")
    public String view(@PathVariable Long id, Model model) {
        // check if client exists
        Client client = clientService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
        model.addAttribute("client", client);
        return "client/view";
    }

    // show empty form for adding a new client
    @GetMapping("/new")
    public String newForm(Model model) {
        model.addAttribute("client", new Client());
        return "client/form";
    }

    // save the new client to the database
    @PostMapping("/new")
    public String create(@Valid @ModelAttribute Client client,
                         BindingResult result,
                         RedirectAttributes flash) {
        // if there are validation errors, show them in the form
        if (result.hasErrors()) {
            return "client/form";
        }
        clientService.save(client);
        flash.addFlashAttribute("successMsg", "Client added successfully!");
        return "redirect:/clients";
    }

    // show form pre-filled with existing client data
    @GetMapping("/{id}/edit")
    public String editForm(@PathVariable Long id, Model model) {
        // check if client exists
        Client client = clientService.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
        model.addAttribute("client", client);
        return "client/form";
    }

    // update the client in the database
    @PostMapping("/{id}/edit")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute Client client,
                         BindingResult result,
                         RedirectAttributes flash) {
        if (result.hasErrors()) {
            return "client/form";
        }
        clientService.update(id, client);
        flash.addFlashAttribute("successMsg", "Client updated successfully!");
        return "redirect:/clients";
    }

    // delete the client from the database
    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes flash) {
        clientService.deleteById(id);
        flash.addFlashAttribute("successMsg", "Client deleted.");
        return "redirect:/clients";
    }
}
