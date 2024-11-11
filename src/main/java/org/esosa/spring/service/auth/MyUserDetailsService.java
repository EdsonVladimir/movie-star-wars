package org.esosa.spring.service.auth;

import org.esosa.spring.model.user.User;
import org.esosa.spring.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collections;

/**
 * Servicio personalizado para cargar detalles de un usuario basado en el nombre de usuario.
 * Esta clase implementa la interfaz UserDetailsService de Spring Security, cumpliendo con el principio SOLID de
 * Responsabilidad Única, ya que se enfoca exclusivamente en cargar y gestionar los detalles de autenticación de un usuario.
 *
 * Esta clase permite que el sistema autentique a los usuarios basados en la lógica definida.
 *
 * @author Edson
 */
@Service
public class MyUserDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    public MyUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /**
     * Carga los detalles de un usuario por su nombre de usuario.
     *
     * @param username Nombre de usuario que se desea autenticar
     * @return Un objeto UserDetails que contiene la información del usuario autenticado
     * @throws UsernameNotFoundException si el usuario no se encuentra en el sistema
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(username);

        if (user == null) {
            throw new UsernameNotFoundException("User not found: " + username);
        }

        // Aquí estamos creando un objeto de tipo UserDetails a partir del objeto User
        return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getPassword(), Collections.emptyList());
    }
}