package org.esosa.spring.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.Arrays;
import java.util.List;

/**
 * Configuración para la integración de Swagger en la aplicación.
 *
 * Swagger es una herramienta para la generación automática de documentación de la API REST,
 * permitiendo visualizar y probar los endpoints de la aplicación.
 *
 * SwaggerConfig define los esquemas de autenticación y los metadatos que se muestran en
 * la documentación genera
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Configuration
@EnableWebMvc
public class SwaggerConfig implements WebMvcConfigurer {

    /**
     * Configura el bean principal para Swagger, especificando el tipo de documentación, el paquete base
     * de los controladores y las configuraciones de seguridad.
     *
     * @return una instancia configurada de {@link Docket} para Swagger 2
     */
    @Bean
    public Docket api() {
        return new Docket(DocumentationType.SWAGGER_2)
                .select()
                .apis(RequestHandlerSelectors.basePackage("org.esosa.spring.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfoMetaData())
                .securitySchemes(Arrays.asList(apiKey()))
                .securityContexts(Arrays.asList(securityContext()));
    }

    /**
     * Proporciona los metadatos de la API, que aparecerán en la página principal de Swagger.
     * Incluye información como el título, descripción, contacto y licencia de la API.
     *
     * @return una instancia de {@link ApiInfo} con los detalles de la API
     */
    private ApiInfo apiInfoMetaData() {

        return new ApiInfoBuilder().title("MOVIES STAR WARS")
                .description("API Endpoint Decoration")
                .contact(new Contact("edson.sosa", "https://edson-sosa.dev", "edson.vladimir.sosa@gmail.com"))
                .license("Apache 2.0")
                .licenseUrl("http://www.apache.org/licenses/LICENSE-2.0.html")
                .version("1.0.0")
                .build();
    }


    /**
     * Define el esquema de seguridad para la autenticación con token Bearer.
     * Swagger incluirá este esquema en el encabezado de las solicitudes.
     *
     * @return una instancia de {@link ApiKey} que define el esquema de autenticación Bearer
     */
    private ApiKey apiKey() {
        return new ApiKey("Bearer", "Authorization", "header");
    }

    /**
     * Define el contexto de seguridad para Swagger, que se aplica globalmente a todos los endpoints.
     *
     * @return una instancia de {@link SecurityContext} configurada con las referencias de seguridad
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder()
                .securityReferences(defaultAuth())
                .build();
    }

    /**
     * Proporciona el alcance de autorización para la autenticación.
     * Este método define el acceso global a todos los recursos de la API.
     *
     * @return una lista de {@link SecurityReference} con los alcances de autorización
     */
    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");
        AuthorizationScope[] authorizationScopes = new AuthorizationScope[1];
        authorizationScopes[0] = authorizationScope;
        return Arrays.asList(new SecurityReference("Bearer", authorizationScopes));

    }
}
