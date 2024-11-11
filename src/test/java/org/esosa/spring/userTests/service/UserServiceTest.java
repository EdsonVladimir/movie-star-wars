package org.esosa.spring.userTests.service;

import org.esosa.spring.dto.user.UserDto;
import org.esosa.spring.model.user.User;
import org.esosa.spring.service.user.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    public void testCreateUser() {
        UserDto userDto = new UserDto();

        userDto.setName("dos");
        userDto.setEmail("dos@gmail.com");
        userDto.setPassword("miContraseña");

        User savedUser = userService.saveUser(userDto);

        assertNotNull(savedUser.getId());
    }
}
