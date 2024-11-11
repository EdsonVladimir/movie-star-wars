package org.esosa.spring.dto.auth;

import javax.persistence.Id;

/**
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
public class AuthResponseDto {
    @Id
    private Long id;
    private String email;
    private String name;
    private String token;

    public AuthResponseDto() {
    }

    public AuthResponseDto(Long id, String email, String name, String token) {
        this.id = id;
        this.email = email;
        this.name = name;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
