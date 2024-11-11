package org.esosa.spring.controller.user;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.esosa.spring.dto.user.UserRegisterDto;
import org.esosa.spring.dto.user.UserResponseDto;
import org.esosa.spring.model.user.User;
import org.esosa.spring.service.user.UserService;
import org.esosa.spring.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

/**
 * Controlador encargado de gestionar las operaciones relacionadas con los usuarios.
 * Este controlador maneja las solicitudes para registrar un usuario, obtener todos los usuarios,
 * obtener un usuario por su ID o correo electrónico y manejar errores durante la creación de usuarios.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@RestController
@RequestMapping("/users")
@Api(value = "Controlador de Ususarios", description = "Operaciones relacionadas a Usuarios")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    /**
     * Método para registrar un nuevo usuario.
     * Si el correo electrónico ya está registrado, se retorna un error 400. En caso de éxito, se retorna el
     * usuario recién creado con el código 201.
     *
     * @param userDto contiene la información del usuario a registrar
     * @return una respuesta HTTP con el usuario creado o un mensaje de error
     */
    @ApiOperation(value = "Registrar usuario", notes = "Devuelve el usuario creado")
    @PostMapping
    public ResponseEntity<Object> createUser(@Valid @RequestBody UserRegisterDto userDto) {
        try {
            if (userService.isEmailRegistered(userDto.getEmail())) {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El email ya se encuentra registrado.");
            }

            User user = userService.saveUser(userDto);
            return ResponseEntity.status(HttpStatus.CREATED).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST: " + e.getMessage());
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getErrorMessage());
        }
    }

    /**
     * Método para obtener todos los usuarios registrados.
     * Si ocurre un error al obtener los usuarios, se retorna un error 500 con el mensaje correspondiente.
     *
     * @return una lista de usuarios registrados o un mensaje de error
     */
    @ApiOperation(value = "Obtener usuarios", notes = "Devuelve todos los usuarios registrados")
    @GetMapping()
    public ResponseEntity<Object> getAllUsers() {
        try {
            List<UserResponseDto> users = userService.getAllUsers();
            return ResponseEntity.status(HttpStatus.OK).body(users);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST: " + e.getMessage());
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getErrorMessage());
        }
    }

    /**
     * Método para obtener un usuario por su ID.
     * Si el usuario con el ID proporcionado no se encuentra, se retorna un error 400.
     *
     * @param id el ID del usuario a obtener
     * @return los detalles del usuario o un mensaje de error
     */
    @ApiOperation(value = "Obtener usuario", notes = "Devuelve usuario filtrado por id")
    @GetMapping("/{id}")
    public ResponseEntity<Object> getUser(@PathVariable Long id) {
        try {
            UserResponseDto user = userService.getUserById(id);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST: " + e.getMessage());
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getErrorMessage());
        }
    }

    /**
     * Método para obtener un usuario por su correo electrónico.
     * Si el usuario con el correo electrónico proporcionado no se encuentra, se retorna un error 400.
     *
     * @param email el correo electrónico del usuario a obtener
     * @return los detalles del usuario o un mensaje de error
     */
    @ApiOperation(value = "Obtener usuario", notes = "Devuelve usuario filtrado por email")
    @GetMapping("/email/{email}")
    public ResponseEntity<Object> getUserByEmail(@PathVariable String email) {
        try {
            UserResponseDto user = userService.getUserByEmail(email);
            return ResponseEntity.status(HttpStatus.OK).body(user);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST: " + e.getMessage());
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getErrorMessage());
        }
    }
}
