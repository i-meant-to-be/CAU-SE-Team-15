package com.cause15.issuetrackerserver.service;

import com.cause15.issuetrackerserver.model.User;
import com.cause15.issuetrackerserver.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceTest {
    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllUsers() {
        userService.getAllUsers();
        verify(userRepository, times(1)).findAll();
    }

    @Test
    void testGetUserById() {
        UUID userId = UUID.fromString("5cc7a063-7c5d-41f1-b7a2-bd8313919c15");

        User user = new User();
        user.setId(userId);
        when(userRepository.findById(userId)).thenReturn(Optional.of(user));

        Optional<User> foundUser = userService.getUserById(userId);
        assertNotNull(foundUser);
        assertEquals(userId, foundUser.get().getId());
    }

    @Test
    void testCreateUser() {
        User user = new User();
        user.setName("John Doe");
        when(userRepository.save(user)).thenReturn(user);

        User createdUser = userService.createUser(user);
        assertNotNull(createdUser);
        assertEquals("John Doe", createdUser.getName());
    }

    @Test
    void testUpdateUser() {
        UUID userId = UUID.fromString("5cc7a063-7c5d-41f1-b7a2-bd8313919c15");
        User user = new User();
        user.setId(userId);
        user.setName("John Doe Updated");

        when(userRepository.existsById(userId)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);

        User updatedUser = userService.updateUser(userId, user);
        assertNotNull(updatedUser);
        assertEquals("John Doe Updated", updatedUser.getName());
    }

    @Test
    void testDeleteUser() {
        UUID userId = UUID.fromString("5cc7a063-7c5d-41f1-b7a2-bd8313919c15");

        when(userRepository.existsById(userId)).thenReturn(true);

        boolean result = userService.deleteUser(userId);
        assertTrue(result);
        verify(userRepository, times(1)).deleteById(userId);
    }

    @Test
    void testDeleteUserNotFound() {
        UUID userId = UUID.fromString("5cc7a063-7c5d-41f1-b7a2-bd8313919c15");

        when(userRepository.existsById(userId)).thenReturn(false);

        boolean result = userService.deleteUser(userId);
        assertFalse(result);
        verify(userRepository, never()).deleteById(userId);
    }
}
