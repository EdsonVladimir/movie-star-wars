package org.esosa.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * Clase de configuración de Spring para definir beans de aplicación.
 *
 * Esta clase contiene la configuración necesaria para crear y registrar
 * un bean de {@link RestTemplate}, que es útil para realizar llamadas HTTP
 * en otros servicios o API REST desde la aplicación.
 *
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Configuration
public class AppConfig {
    /**
     * Crea y registra una instancia de {@link RestTemplate} como un bean en el
     * contexto de Spring.
     *
     * El {@link RestTemplate} permite realizar solicitudes HTTP de manera
     * sencilla, proporcionando métodos para realizar operaciones GET, POST, PUT,
     * DELETE, entre otros, en APIs RESTful. Este bean estará disponible para
     * inyección en otras clases de la aplicación.
     *
     * @return una instancia de {@link RestTemplate} configurada para el uso en
     *         la aplicación.
     */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
