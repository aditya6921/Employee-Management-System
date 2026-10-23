package com.employee.service;

import com.employee.entity.Owner;
import com.employee.repository.OwnerRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class OwnerService {

    private final OwnerRepository ownerRepository;
    private final PasswordEncoder passwordEncoder;

    public OwnerService(
            OwnerRepository ownerRepository,
            PasswordEncoder passwordEncoder) {

        this.ownerRepository = ownerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Owner registerOwner(Owner owner) {

        if (owner.getEmail() == null || owner.getEmail().isBlank()
                || owner.getPassword() == null || owner.getPassword().length() < 8) {
            throw new IllegalArgumentException("A valid email and password of at least 8 characters are required");
        }

        if (ownerRepository.findByEmail(owner.getEmail()).isPresent()) {
            throw new IllegalArgumentException("An account with this email already exists");
        }

        owner.setPassword(
                passwordEncoder.encode(owner.getPassword())
        );

        return ownerRepository.save(owner);
    }

    public Owner login(String email, String password) {

        Owner owner = ownerRepository.findByEmail(email)
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                password,
                owner.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        return owner;
    }
}
