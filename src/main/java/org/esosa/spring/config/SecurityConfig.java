package org.esosa.spring.config;

import org.esosa.spring.util.JwtRequestFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

/**
 * Configuración de seguridad para la aplicación, extendiendo {@link WebSecurityConfigurerAdapter}
 * para personalizar la seguridad basada en HTTP.
 * <p>
 * Esta configuración define los endpoints públicos y asegura el resto de las rutas.
 * También agrega un filtro JWT para manejar la autenticación basada en tokens.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtRequestFilter jwtRequestFilter;
    private final UserDetailsService userDetailsService;

    public SecurityConfig(JwtRequestFilter jwtRequestFilter, UserDetailsService userDetailsService) {
        this.jwtRequestFilter = jwtRequestFilter;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Define el codificador de contraseñas como un bean de {@link BCryptPasswordEncoder}.
     * <p>
     * BCrypt se utiliza para almacenar contraseñas de forma segura mediante hashing.
     *
     * @return una instancia de {@link PasswordEncoder} que utiliza BCrypt
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Configura las reglas de seguridad para las rutas de la aplicación.
     * <p>
     * Permite el acceso público a ciertos endpoints, como el de autenticación y la documentación
     * de Swagger, mientras asegura que todas las demás rutas requieren autenticación.
     *
     * @param http instancia de {@link HttpSecurity} utilizada para configurar la seguridad HTTP
     * @throws Exception si ocurre un error durante la configuración
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers(
                        "/api/authenticate",
                        "/v2/api-docs",
                        "/swagger-resources/**",
                        "/swagger-ui.html",
                        "/webjars/**",
                        "/swagger-ui/**",
                        "/users/**")
                .permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .csrf()
                .disable();

        http.addFilterBefore(jwtRequestFilter, UsernamePasswordAuthenticationFilter.class);
    }

    /**
     * Expone el bean de {@link AuthenticationManager} para inyección en otros componentes de la aplicación.
     *
     * @return el bean de {@link AuthenticationManager}
     * @throws Exception si ocurre un error durante la creación del bean
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }
}
