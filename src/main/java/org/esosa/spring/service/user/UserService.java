package org.esosa.spring.service.user;

import org.esosa.spring.dto.user.UserDto;
import org.esosa.spring.dto.user.UserResponseDto;
import org.esosa.spring.model.user.User;
import org.esosa.spring.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Servicio que gestiona las operaciones relacionadas con los usuarios.
 * Este servicio proporciona métodos para registrar nuevos usuarios,
 * obtener usuarios por ID o correo electrónico, y verificar si un correo
 * electrónico ya está registrado.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Service
public class UserService {

    private static final boolean _TRUE = true;

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Registra un nuevo usuario en el sistema.
     * Se realiza la codificación de la contraseña antes de guardar el usuario.
     *
     * @param userDto objeto que contiene los datos del usuario a registrar
     * @return el usuario registrado
     */
    public User saveUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = new User();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encodedPassword);
        user.setEnabled(_TRUE);
        
        return userRepository.save(user);
    }

    /**
     * Obtiene todos los usuarios registrados en el sistema.
     *
     * @return una lista de objetos {@link UserResponseDto} que representan a los usuarios
     */
    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> new UserResponseDto(user.getId(), user.getEmail(), user.getName(), user.isEnabled())).collect(Collectors.toList());
    }

    /**
     * Obtiene un usuario por su ID.
     *
     * @param id el ID del usuario a obtener
     * @return un objeto {@link UserResponseDto} con los datos del usuario
     */
    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return new UserResponseDto(user.getId(), user.getEmail(), user.getName(), user.isEnabled());
    }

    /**
     * Obtiene un usuario por su correo electrónico.
     * Lanza una excepción si el correo no está registrado.
     *
     * @param email el correo electrónico del usuario a obtener
     * @return un objeto {@link UserResponseDto} con los datos del usuario
     * @throws UsernameNotFoundException si el correo no está registrado
     */
    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("The email is not registered.");
        }

        return new UserResponseDto(user.getId(), user.getEmail(), user.getName(), user.isEnabled());
    }

    /**
     * Verifica si un correo electrónico ya está registrado en el sistema.
     *
     * @param email el correo electrónico a verificar
     * @return {@code true} si el correo está registrado, {@code false} en caso contrario
     */
    public boolean isEmailRegistered(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        return user.isPresent();
    }
}
