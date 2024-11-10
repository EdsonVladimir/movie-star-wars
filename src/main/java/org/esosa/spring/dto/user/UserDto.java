package org.esosa.spring.dto.user;

import javax.persistence.Id;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
public class UserDto {

    @Id
    private Long id;

    @NotNull(message = "El email no puede ser nulo")
    @NotEmpty(message = "El email del producto no puede estar vacío")
    private String email;

    @NotNull(message = "El nombre no puede ser nulo")
    @NotEmpty(message = "El nombre del producto no puede estar vacío")
    private String name;

    @NotNull(message = "La contraseña no puede ser nulo")
    @NotEmpty(message = "La contraseña del producto no puede estar vacío")
    private String password;

    private boolean enabled;

    public UserDto() {
    }

    public UserDto(Long id, String email, String name, String password, boolean enabled) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.password = password;
        this.enabled = enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean getEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

}
