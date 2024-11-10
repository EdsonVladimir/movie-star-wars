package org.esosa.spring.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.function.Function;

/**
 * Clase encargada de generar JWT
 * Recibimos el nombre del usuario y lo transfromamos en un token unico
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Component
public class JwtUtil {

    private static final int _LIFETIME_TOKEN = 1000 * 60 * 60 * 10;
    private static final String _SECRET_KEY = "secret_key_movie_user";

    /**
     * Generar in token JWT para un usuario especificado con un tiempo de duracion de 10 horas.
     *
     * @param username nombre del usuario
     * @return un token JWT firmado como cadena de texto
     */
    public String generateToken(String username) {
        return Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + _LIFETIME_TOKEN))
                .signWith(SignatureAlgorithm.HS256, _SECRET_KEY)
                .compact();
    }

    /**
     * Extraer el nombre de usuario (subject) del token proporcionado.
     *
     * @param token el token JWT del cual se extrae el nombre
     * @returnel nombre de usuario (subject) del token
     */
    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    /**
     * Valida un token JWT verificando que el nombre de usuario extraído coincida con el nombre de usuario proporcionado
     * y que el token no haya expirado.
     *
     * @param token    el token JWT a validar
     * @param username el nombre de usuario a comparar con el subject del token
     * @return true si el token es válido, false en caso contrario
     */
    public Boolean isTokenValid(String token, String username) {
        final String extractedUsername = extractUsername(token);
        return (extractedUsername.equals(username) && !isTokenExpired(token));
    }

    /**
     * Verifica si el token JWT proporcionado ha expirado comparando la fecha de expiración con la fecha actual.
     *
     * @param token el token JWT a verificar
     * @return true si el token ha expirado, false en caso contrario
     */
    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    /**
     * Extrae la fecha de expiración del token JWT proporcionado.
     *
     * @param token el token JWT del cual se extrae la fecha de expiración
     * @return la fecha de expiración del token
     */
    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    /**
     * Extrae un claim específico del token JWT usando un resolver de claims personalizado.
     *
     * @param token          el token JWT del cual se extrae el claim
     * @param claimsResolver una función para obtener un claim específico de los claims del token
     * @param <T>            el tipo del claim que se está extrayendo
     * @return el claim extraído
     */
    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = Jwts.parser().setSigningKey(_SECRET_KEY).parseClaimsJws(token).getBody();
        return claimsResolver.apply(claims);
    }

}
