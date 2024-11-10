package org.esosa.spring.dto.user;

import com.fasterxml.jackson.annotation.JsonIgnore;

/**
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
public class UserRegisterDto extends UserDto {

    public UserRegisterDto() {
        super();
    }

    public UserRegisterDto(String name, String email, String password, boolean enabled) {
        super(null, email, name, password, enabled);
    }

    @Override
    @JsonIgnore
    public Long getId() {
        return super.getId();
    }

    @Override
    @JsonIgnore
    public boolean getEnabled() {
        return super.getEnabled();
    }
}
