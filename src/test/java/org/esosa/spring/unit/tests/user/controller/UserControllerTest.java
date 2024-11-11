package org.esosa.spring.unit.tests.user.controller;

import org.esosa.spring.controller.user.UserController;
import org.esosa.spring.dto.user.UserRegisterDto;
import org.esosa.spring.dto.user.UserResponseDto;
import org.esosa.spring.model.user.User;
import org.esosa.spring.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class UserControllerTest {
    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    private UserRegisterDto userRegisterDto;
    private UserResponseDto userResponseDto;
    private User user;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Inicializar objetos necesarios para las pruebas
        userRegisterDto = new UserRegisterDto("John Doe", "john@example.com", "password", true);
        userResponseDto = new UserResponseDto(null, "John Doe", "john@example.com", true);
        user = new User("John Doe", "john@example.com", "password");
    }

    /**
     * Test para verificar la creación exitosa de un usuario.
     * Se espera que el servicio retorne el usuario creado con un estado HTTP 201.
     */
    @Test
    void createUser_ShouldReturnCreatedUser_WhenValidUserDtoIsGiven() {
        // Simular la existencia del email y la creación del usuario
        when(userService.isEmailRegistered(userRegisterDto.getEmail())).thenReturn(false);
        when(userService.saveUser(userRegisterDto)).thenReturn(user);

        // Llamar al método del controlador
        ResponseEntity<Object> response = userController.createUser(userRegisterDto);

        // Verificar los resultados
        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(user, response.getBody());

        // Verificar que los métodos del servicio fueron llamados
        verify(userService, times(1)).isEmailRegistered(userRegisterDto.getEmail());
        verify(userService, times(1)).saveUser(userRegisterDto);
    }

    /**
     * Test para verificar que se maneja correctamente el caso donde el email ya está registrado.
     * Se espera un error con estado HTTP 400.
     */
    @Test
    void createUser_ShouldReturnBadRequest_WhenEmailIsAlreadyRegistered() {
        // Simular que el email ya está registrado
        when(userService.isEmailRegistered(userRegisterDto.getEmail())).thenReturn(true);

        // Llamar al método del controlador
        ResponseEntity<Object> response = userController.createUser(userRegisterDto);

        // Verificar los resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("El email ya se encuentra registrado.", response.getBody());

        // Verificar que el método del servicio fue llamado
        verify(userService, times(1)).isEmailRegistered(userRegisterDto.getEmail());
    }

    /**
     * Test para verificar que se retorna la lista de usuarios correctamente.
     * Se espera que el método retorne una lista de usuarios con estado HTTP 200.
     */
    @Test
    void getAllUsers_ShouldReturnUserList_WhenUsersExist() {
        // Crear lista de usuarios para la prueba
        List<UserResponseDto> users = Arrays.asList(userResponseDto);

        // Simular la respuesta del servicio
        when(userService.getAllUsers()).thenReturn(users);

        // Llamar al método del controlador
        ResponseEntity<Object> response = userController.getAllUsers();

        // Verificar los resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(users, response.getBody());

        // Verificar que el método del servicio fue llamado
        verify(userService, times(1)).getAllUsers();
    }

    /**
     * Test para verificar que se retorna un error si no se encuentra un usuario por ID.
     * Se espera un error con estado HTTP 400.
     */
    @Test
    void getUser_ShouldReturnBadRequest_WhenUserNotFound() {
        // Simular que el usuario no existe
        when(userService.getUserById(1L)).thenThrow(new IllegalArgumentException("Usuario no encontrado"));

        // Llamar al método del controlador
        ResponseEntity<Object> response = userController.getUser(1L);

        // Verificar los resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("BAD_REQUEST: Usuario no encontrado", response.getBody());

        // Verificar que el método del servicio fue llamado
        verify(userService, times(1)).getUserById(1L);
    }

    /**
     * Test para verificar la obtención de un usuario por email.
     * Se espera que el método retorne el usuario correspondiente con estado HTTP 200.
     */
    @Test
    void getUserByEmail_ShouldReturnUser_WhenUserExists() {
        // Simular la respuesta del servicio
        when(userService.getUserByEmail("john@example.com")).thenReturn(userResponseDto);

        // Llamar al método del controlador
        ResponseEntity<Object> response = userController.getUserByEmail("john@example.com");

        // Verificar los resultados
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(userResponseDto, response.getBody());

        // Verificar que el método del servicio fue llamado
        verify(userService, times(1)).getUserByEmail("john@example.com");
    }

    /**
     * Test para verificar que se maneja correctamente un error en la obtención de un usuario por email.
     * Se espera un error con estado HTTP 400.
     */
    @Test
    void getUserByEmail_ShouldReturnBadRequest_WhenUserNotFound() {
        // Simular que el usuario no existe
        when(userService.getUserByEmail("john@example.com")).thenThrow(new IllegalArgumentException("Usuario no encontrado"));

        // Llamar al método del controlador
        ResponseEntity<Object> response = userController.getUserByEmail("john@example.com");

        // Verificar los resultados
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("BAD_REQUEST: Usuario no encontrado", response.getBody());

        // Verificar que el método del servicio fue llamado
        verify(userService, times(1)).getUserByEmail("john@example.com");
    }
}
