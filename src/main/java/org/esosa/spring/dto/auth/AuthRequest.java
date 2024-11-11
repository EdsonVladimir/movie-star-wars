package org.esosa.spring.dto.auth;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
public class AuthRequest {
    @NotNull(message = "El email no puede ser nulo")
    @NotEmpty(message = "El email del producto no puede estar vacío")
    private String email;

    @NotNull(message = "La contraseña no puede ser nulo")
    @NotEmpty(message = "La contraseña del producto no puede estar vacío")
    private String password;

    public AuthRequest() {
    }

    @JsonCreator
    public AuthRequest(@JsonProperty("email") String email, @JsonProperty("password") String password) {
        this.email = email;
        this.password = password;
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
}
