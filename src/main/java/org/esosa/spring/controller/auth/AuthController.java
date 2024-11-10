package org.esosa.spring.controller.auth;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.esosa.spring.dto.auth.AuthRequest;
import org.esosa.spring.dto.auth.AuthResponseDto;
import org.esosa.spring.dto.user.UserResponseDto;
import org.esosa.spring.service.auth.MyUserDetailsService;
import org.esosa.spring.service.user.UserService;
import org.esosa.spring.util.JwtUtil;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@RestController
@Api(value = "Autentificacion de Usuario", description = "Validacion de usaurio registrado y generacion de token")
public class AuthController {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final MyUserDetailsService userDetailsService;
    private final UserService userService;

    public AuthController(AuthenticationManager authenticationManager,
                          JwtUtil jwtUtil,
                          MyUserDetailsService userDetailsService, UserService userService) {
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
        this.userService = userService;
    }

    @ApiOperation(value = "Validacion de usaurio", notes = "Validacion de usaurio registrado se retorna los datos del usuario registrado mas su token")
    @PostMapping("/authenticate")
    public ResponseEntity<Object> createAuthenticationToken(@Valid @RequestBody AuthRequest authRequest) throws Exception {
        try {
            UserResponseDto user = userService.getUserByEmail(authRequest.getEmail());

            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
            );
            final UserDetails userDetails = userDetailsService.loadUserByUsername(authRequest.getEmail());

            final String jwt = jwtUtil.generateToken(userDetails.getUsername());

            AuthResponseDto authResponseDto = new AuthResponseDto(user.getId(), user.getName(), user.getEmail(), jwt);
            return ResponseEntity.status(HttpStatus.OK).body(authResponseDto);
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("BAD_REQUEST: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Incorrect username or password");
        }
    }
}

