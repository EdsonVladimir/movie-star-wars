package org.esosa.spring.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.esosa.spring.service.movie.MovieStarWarsFilmService;
import org.esosa.spring.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controlador encargado de manejar todas las operaciones relacionadas con las films de Star Wars.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Api(tags = "Movie Star Wars Film API", value = "Funcionalidades para listar Film", description = "Operaciones relacionadas a film")
@RestController
@RequestMapping("/api/starwars")
public class MovieStarWarsFilmController {
    private final MovieStarWarsFilmService movieStarWarsFilmService;

    public MovieStarWarsFilmController(MovieStarWarsFilmService movieStarWarsFilmService) {
        this.movieStarWarsFilmService = movieStarWarsFilmService;
    }

    /**
     * Listar films por página.
     * Este método devuelve una lista de films paginada.
     *
     * @param page El número de la página a recuperar.
     * @return ResponseEntity que contiene la lista de films.
     */
    @ApiOperation(value = "Listar Film por pagina", notes = "Devuelve lista de Films por pagina")
    @GetMapping("/film")
    public ResponseEntity<List<Object>> getFilm(@RequestParam(defaultValue = "1") int page) {
        try {
            List<Object> film = movieStarWarsFilmService.getFilm(page);
            return ResponseEntity.ok(film);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(error.getErrorMessage()));
        }
    }

    /**
     * Obtener una film específica por ID.
     * Este método obtiene una film filtrada por su ID.
     *
     * @param id El ID de la film a recuperar.
     * @return ResponseEntity que contiene el objeto de la film.
     */
    @ApiOperation(value = "Filtrar Film por id", notes = "Devuelve Film filtrado por Id")
    @GetMapping("/film/{id}")
    public ResponseEntity<Object> getFilmById(@PathVariable int id) {
        try {
            Object film = movieStarWarsFilmService.getFilmById(id);
            return ResponseEntity.ok(film);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getErrorMessage());
        }
    }

    /**
     * Buscar films por nombre.
     * Este método filtra a las films según el nombre proporcionado.
     *
     * @param search El nombre de la film a buscar.
     * @return ResponseEntity que contiene la lista de films que coinciden con la búsqueda.
     */
    @ApiOperation(value = "Filtrar Film por nombre", notes = "Devuelve Films filtrado por nombre")
    @GetMapping("/film/search")
    public ResponseEntity<List<Object>> getFilmBySearch(@RequestParam String search) {
        try {
            List<Object> film = movieStarWarsFilmService.getFilmByName(search);
            return ResponseEntity.ok(film);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(error.getErrorMessage()));
        }
    }
}
