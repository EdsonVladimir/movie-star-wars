package org.esosa.spring.unit.tests.user.service;

import org.esosa.spring.dto.user.UserDto;
import org.esosa.spring.dto.user.UserResponseDto;
import org.esosa.spring.model.user.User;
import org.esosa.spring.repository.UserRepository;
import org.esosa.spring.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    @Test
    void saveUser_ShouldSaveUser_WhenValidUserDtoIsGiven() {
        // Arrange
        UserDto userDto = new UserDto(null, "John Doe", "john@example.com", "password123", true);
        String encodedPassword = "encodedPassword";

        when(passwordEncoder.encode(userDto.getPassword())).thenReturn(encodedPassword);
        when(userRepository.save(any(User.class))).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        User savedUser = userService.saveUser(userDto);

        // Assert
        assertNotNull(savedUser);
        assertEquals("John Doe", savedUser.getName());
        assertEquals("john@example.com", savedUser.getEmail());
        assertEquals(encodedPassword, savedUser.getPassword());
        assertTrue(savedUser.isEnabled());
        verify(userRepository).save(any(User.class));  // Verifica que el repositorio se llame
    }

    @Test
    void getAllUsers_ShouldReturnUserResponseDtoList_WhenUsersExist() {
        // Arrange
        User user1 = new User("John", "john@example.com", "encodedPassword");
        User user2 = new User("Jane", "jane@example.com", "encodedPassword");
        when(userRepository.findAll()).thenReturn(Arrays.asList(user1, user2));

        // Act
        List<UserResponseDto> users = userService.getAllUsers();

        // Assert
        assertNotNull(users);
        assertEquals(2, users.size());
        assertEquals("john@example.com", users.get(0).getEmail());
        assertEquals("jane@example.com", users.get(1).getEmail());
    }

    @Test
    void getUserByEmail_ShouldReturnUserResponseDto_WhenUserExists() {
        // Arrange
        User user = new User("John", "john@example.com", "encodedPassword");
        when(userRepository.findByEmail("john@example.com")).thenReturn(user);

        // Act
        UserResponseDto userResponse = userService.getUserByEmail("john@example.com");

        // Assert
        assertNotNull(userResponse);
        assertEquals("john@example.com", userResponse.getEmail());
        assertEquals("John", userResponse.getName());
    }

    @Test
    void getUserByEmail_ShouldThrowUsernameNotFoundException_WhenUserDoesNotExist() {
        // Arrange
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);

        // Act & Assert
        assertThrows(UsernameNotFoundException.class, () -> userService.getUserByEmail("john@example.com"));
    }

    @Test
    void isEmailRegistered_ShouldReturnTrue_WhenEmailIsRegistered() {
        // Arrange
        when(userRepository.findByEmail("john@example.com")).thenReturn(new User("John", "john@example.com", "encodedPassword"));

        // Act
        boolean result = userService.isEmailRegistered("john@example.com");

        // Assert
        assertTrue(result);
    }

    @Test
    void isEmailRegistered_ShouldReturnFalse_WhenEmailIsNotRegistered() {
        // Arrange
        when(userRepository.findByEmail("john@example.com")).thenReturn(null);

        // Act
        boolean result = userService.isEmailRegistered("john@example.com");

        // Assert
        assertFalse(result);
    }
}
