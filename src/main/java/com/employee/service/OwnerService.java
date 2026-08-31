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