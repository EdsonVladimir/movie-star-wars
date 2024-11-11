package org.esosa.spring.repository;

import org.esosa.spring.model.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * Repositorio que maneja las operaciones de persistencia para la entidad {@link User}.
 * Este repositorio hereda de {@link JpaRepository} y proporciona acceso a los datos
 * de los usuarios, permitiendo la búsqueda por correo electrónico.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
public interface UserRepository extends JpaRepository<User, Long> {

    /**
     * Método para encontrar un usuario por su correo electrónico.
     *
     * @param email el correo electrónico del usuario a buscar
     * @return el usuario encontrado o {@code null} si no se encuentra
     */
    User findByEmail(String email);
}
