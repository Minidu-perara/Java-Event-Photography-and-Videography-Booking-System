package com.photobooking.service;

import com.photobooking.model.Client;
import com.photobooking.repository.ClientRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class ClientService {

    private final ClientRepository clientRepository;

    // ── Read ─────────────────────────────────────────────────
    @Transactional(readOnly = true)
    public List<Client> findAll() {
        return clientRepository.findAll();
    }

    @Transactional(readOnly = true)
    public Optional<Client> findById(Long id) {
        return clientRepository.findById(id);
    }

    @Transactional(readOnly = true)
    public List<Client> search(String name) {
        if (name == null || name.isBlank()) return findAll();
        return clientRepository.searchByName(name.trim());
    }

    // ── Write ────────────────────────────────────────────────
    public Client save(Client client) {
        return clientRepository.save(client);
    }

    public Client update(Long id, Client updated) {
        Client existing = clientRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Client not found: " + id));
        existing.setFirstName(updated.getFirstName());
        existing.setLastName(updated.getLastName());
        existing.setEmail(updated.getEmail());
        existing.setPhone(updated.getPhone());
        existing.setAddress(updated.getAddress());
        return clientRepository.save(existing);
    }

    public void deleteById(Long id) {
        clientRepository.deleteById(id);
    }

    // ── Business ─────────────────────────────────────────────
    @Transactional(readOnly = true)
    public boolean emailExists(String email) {
        return clientRepository.existsByEmail(email);
    }

    @Transactional(readOnly = true)
    public long count() {
        return clientRepository.count();
    }
}
