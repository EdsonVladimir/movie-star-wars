package org.esosa.spring.service.user;

import org.esosa.spring.dto.user.UserResponseDto;
import org.esosa.spring.dto.user.UserDto;
import org.esosa.spring.model.user.User;
import org.esosa.spring.repository.UserRepository;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
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

    public User saveUser(UserDto userDto) {
        String encodedPassword = passwordEncoder.encode(userDto.getPassword());

        User user = new User();

        user.setName(userDto.getName());
        user.setEmail(userDto.getEmail());
        user.setPassword(encodedPassword);
        user.setEnabled(_TRUE);
        return userRepository.save(user);
    }

    public List<UserResponseDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return users.stream().map(user -> new UserResponseDto(user.getId(), user.getEmail(), user.getName(), user.isEnabled())).collect(Collectors.toList());
    }

    public UserResponseDto getUserById(Long id) {
        User user = userRepository.findById(id).orElse(null);
        return new UserResponseDto(user.getId(), user.getEmail(), user.getName(), user.isEnabled());
    }

    public UserResponseDto getUserByEmail(String email) {
        User user = userRepository.findByEmail(email);

        if (user == null) {
            throw new UsernameNotFoundException("The email is not registered.");
        }

        return new UserResponseDto(user.getId(), user.getEmail(), user.getName(), user.isEnabled());
    }

    public boolean isEmailRegistered(String email) {
        Optional<User> user = Optional.ofNullable(userRepository.findByEmail(email));
        return user.isPresent();
    }
}
