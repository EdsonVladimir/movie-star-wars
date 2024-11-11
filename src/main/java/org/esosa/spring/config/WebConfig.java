package org.esosa.spring.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración para el manejo de CORS (Cross-Origin Resource Sharing) en la aplicación.
 *
 * Esta clase permite que clientes externos puedan realizar solicitudes a la API,
 * definiendo los orígenes, métodos y encabezados permitidos.
 *
 * Esta configuración es útil para permitir el acceso a la API desde aplicaciones
 * web alojadas en dominios diferentes al de la API.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Configura las reglas de CORS para la aplicación.
     *
     * Permite solicitudes desde cualquier origen (`allowedOrigins("*")`),
     * con cualquier método HTTP (`allowedMethods("*")`), y cualquier encabezado (`allowedHeaders("*")`).
     * La configuración no permite el envío de credenciales y establece el tiempo de validez de la política en 1 hora.
     *
     * @param registry el registro de CORS donde se añaden las configuraciones de CORS
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                .allowCredentials(false)
                .maxAge(3600);
    }
}
