package org.esosa.spring.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.esosa.spring.service.movie.MovieStarWarsStarshipService;
import org.esosa.spring.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controlador encargado de manejar todas las operaciones relacionadas con las starships de Star Wars.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Api(tags = "Movie Star Wars Starship API", value = "Funcionalidades para listar Starship", description = "Operaciones relacionadas a starship")
@RestController
@RequestMapping("/api/starwars")
public class MovieStarWarsStarshipController {
    private final MovieStarWarsStarshipService movieStarWarsStarshipService;

    public MovieStarWarsStarshipController(MovieStarWarsStarshipService movieStarWarsStarshipService) {
        this.movieStarWarsStarshipService = movieStarWarsStarshipService;
    }

    /**
     * Listar starships por página.
     * Este método devuelve una lista de starships paginada.
     *
     * @param page El número de la página a recuperar.
     * @return ResponseEntity que contiene la lista de starships.
     */
    @ApiOperation(value = "Listar Starship por pagina", notes = "Devuelve lista de Starships por pagina")
    @GetMapping("/starship")
    public ResponseEntity<List<Object>> getStarship(@RequestParam(defaultValue = "1") int page) {
        try {
            List<Object> starship = movieStarWarsStarshipService.getStarship(page);
            return ResponseEntity.ok(starship);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(error.getErrorMessage()));
        }
    }

    /**
     * Obtener una starship específica por ID.
     * Este método obtiene una starship filtrada por su ID.
     *
     * @param id El ID de la starship a recuperar.
     * @return ResponseEntity que contiene el objeto de la starship.
     */
    @ApiOperation(value = "Filtrar Starship por id", notes = "Devuelve Starship filtrado por Id")
    @GetMapping("/starship/{id}")
    public ResponseEntity<Object> getStarshipById(@PathVariable int id) {
        try {
            Object person = movieStarWarsStarshipService.getStarshipById(id);
            return ResponseEntity.ok(person);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getErrorMessage());
        }
    }

    /**
     * Buscar starships por nombre.
     * Este método filtra a las starships según el nombre proporcionado.
     *
     * @param search El nombre de la starship a buscar.
     * @return ResponseEntity que contiene la lista de starships que coinciden con la búsqueda.
     */
    @ApiOperation(value = "Filtrar Starship por nombre", notes = "Devuelve Starships filtrado por nombre")
    @GetMapping("/starship/search")
    public ResponseEntity<List<Object>> getStarshipBySearch(@RequestParam String search) {
        try {
            List<Object> starship = movieStarWarsStarshipService.getStarshipByName(search);
            return ResponseEntity.ok(starship);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(error.getErrorMessage()));
        }
    }
}
