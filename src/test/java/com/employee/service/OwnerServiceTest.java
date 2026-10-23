
package com.employee.service;

import com.employee.entity.Owner;
import com.employee.repository.OwnerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OwnerServiceTest {

    @Mock
    private OwnerRepository ownerRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private OwnerService ownerService;

    private Owner owner;

    @BeforeEach
    void setUp() {
        owner = new Owner();
        owner.setEmail("owner@example.com");
        owner.setPassword("rawPassword123");
    }

    @Test
    void registerOwner_shouldEncodePasswordAndSaveOwner() {
        when(passwordEncoder.encode("rawPassword123"))
                .thenReturn("encodedPassword");

        when(ownerRepository.save(owner))
                .thenReturn(owner);

        Owner result = ownerService.registerOwner(owner);

        assertNotNull(result);
        assertEquals("encodedPassword", result.getPassword());

        verify(passwordEncoder).encode("rawPassword123");
        verify(ownerRepository).save(owner);
    }

    @Test
    void login_shouldReturnOwner_whenCredentialsAreValid() {
        owner.setPassword("encodedPassword");

        when(ownerRepository.findByEmail("owner@example.com"))
                .thenReturn(Optional.of(owner));

        when(passwordEncoder.matches(
                "rawPassword123", "encodedPassword"))
                .thenReturn(true);

        Owner result = ownerService.login(
                "owner@example.com", "rawPassword123");

        assertNotNull(result);
        assertEquals("owner@example.com", result.getEmail());

        verify(ownerRepository).findByEmail("owner@example.com");
        verify(passwordEncoder).matches(
                "rawPassword123", "encodedPassword");
    }

    @Test
    void login_shouldThrowException_whenEmailDoesNotExist() {
        when(ownerRepository.findByEmail("unknown@example.com"))
                .thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> ownerService.login(
                        "unknown@example.com", "rawPassword123")
        );

        assertEquals(
                "Invalid email or password",
                exception.getMessage());

        verify(passwordEncoder, never())
                .matches(anyString(), anyString());
    }

    @Test
    void login_shouldThrowException_whenPasswordIsIncorrect() {
        owner.setPassword("encodedPassword");

        when(ownerRepository.findByEmail("owner@example.com"))
                .thenReturn(Optional.of(owner));

        when(passwordEncoder.matches(
                "wrongPassword", "encodedPassword"))
                .thenReturn(false);

        RuntimeException exception = assertThrows(
                RuntimeException.class,
                () -> ownerService.login(
                        "owner@example.com", "wrongPassword")
        );

        assertEquals(
                "Invalid email or password",
                exception.getMessage());
    }
}
