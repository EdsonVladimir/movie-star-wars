package org.esosa.spring.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
public class UserResponseDto extends UserDto {

    public UserResponseDto() {
        super();
    }

    public UserResponseDto(Long id, String name, String email, boolean enabled) {
        super(id, email, name, null, enabled);
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return super.getPassword();
    }
}
