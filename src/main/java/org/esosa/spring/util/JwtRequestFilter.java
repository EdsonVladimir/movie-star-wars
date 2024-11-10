package org.esosa.spring.util;

import org.esosa.spring.service.auth.MyUserDetailsService;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Filtro que intercepta cada solicitud HTTP para verificar y validar el token JWT presente en el encabezado.
 * Este filtro sigue el principio de SRP (Responsabilidad Única) del principio SOLID,
 * ya que está dedicado exclusivamente a manejar la autenticación del token en cada solicitud.
 * <p>
 * Al extender OncePerRequestFilter, se asegura de que este filtro se ejecute una sola vez por solicitud.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Component
public class JwtRequestFilter extends OncePerRequestFilter {

    private final JwtUtil jwtUtil; // Componente de utilidad para operaciones con tokens JWT
    private final MyUserDetailsService userDetailsService; // Servicio para cargar detalles de usuario

    public JwtRequestFilter(JwtUtil jwtUtil, MyUserDetailsService userDetailsService) {
        this.jwtUtil = jwtUtil;
        this.userDetailsService = userDetailsService;
    }

    /**
     * Metodo principal que intercepta cada solicitud entrante, valida el token JWT y configura
     * la autenticación en el contexto de seguridad si el token es válido.
     *
     * @param request  La solicitud HTTP entrante
     * @param response La respuesta HTTP saliente
     * @param chain    La cadena de filtros en la que se incluye este filtro
     * @throws ServletException si ocurre un error en el procesamiento de la solicitud
     * @throws IOException      si ocurre un error de E/S
     */
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
            throws ServletException, IOException {

        final String authorizationHeader = request.getHeader("Authorization");

        String username = null;
        String jwt = null;

        // Verifica si el encabezado de autorización contiene un token JWT y lo extrae si está presente
        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7); // Extrae el token quitando el prefijo "Bearer "
            username = jwtUtil.extractUsername(jwt); // Extrae el nombre de usuario del token
        }

        // Si se extrajo un nombre de usuario y no hay autenticación en el contexto de seguridad, verifica el token
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);

            // Valida el token y configura la autenticación en el contexto de seguridad si el token es válido
            if (jwtUtil.isTokenValid(jwt, userDetails.getUsername())) {
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }
        }
        chain.doFilter(request, response); // Continúa la cadena de filtros
    }
}
