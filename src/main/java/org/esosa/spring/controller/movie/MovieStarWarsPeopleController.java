package org.esosa.spring.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.esosa.spring.service.movie.MovieStarWarsPeopleService;
import org.esosa.spring.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controlador encargado de manejar todas las operaciones relacionadas con las personas de Star Wars.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Api(tags = "Movie Star Wars People API", value = "Funcionalidades para listar People", description = "Operaciones relacionadas a people  necesita iniciar sesion para obtener Datos")
@RestController
@RequestMapping("/api/starwars")
public class MovieStarWarsPeopleController {
    private final MovieStarWarsPeopleService movieStarWarsService;

    public MovieStarWarsPeopleController(MovieStarWarsPeopleService movieStarWarsService) {
        this.movieStarWarsService = movieStarWarsService;
    }

    /**
     * Listar personas por página.
     * Este método devuelve una lista de personas paginada.
     *
     * @param page El número de la página a recuperar.
     * @return ResponseEntity que contiene la lista de personas.
     */
    @ApiOperation(value = "Listar People por pagina", notes = "Devuelve lista de Peoples por pagina")
    @GetMapping("/people")
    public ResponseEntity<List<Object>> getPeople(@RequestParam(defaultValue = "1") int page) {
        try {
            List<Object> people = movieStarWarsService.getPeople(page);
            return ResponseEntity.ok(people);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(error.getErrorMessage()));
        }
    }

    /**
     * Obtener una persona específica por ID.
     * Este método obtiene una persona filtrada por su ID.
     *
     * @param id El ID de la persona a recuperar.
     * @return ResponseEntity que contiene el objeto de la persona.
     */
    @ApiOperation(value = "Filtrar People por id", notes = "Devuelve People filtrado por Id")
    @GetMapping("/people/{id}")
    public ResponseEntity<Object> getPeopleById(@PathVariable int id) {
        try {
            Object person = movieStarWarsService.getPeopleById(id);
            return ResponseEntity.ok(person);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getErrorMessage());
        }
    }

    /**
     * Buscar personas por nombre.
     * Este método filtra a las personas según el nombre proporcionado.
     *
     * @param search El nombre de la persona a buscar.
     * @return ResponseEntity que contiene la lista de personas que coinciden con la búsqueda.
     */
    @ApiOperation(value = "Filtrar People por nombre", notes = "Devuelve Peoples filtrado por nombre")
    @GetMapping("/people/search")
    public ResponseEntity<List<Object>> getPeopleBySearch(@RequestParam String search) {
        try {
            List<Object> people = movieStarWarsService.getPeopleByName(search);
            return ResponseEntity.ok(people);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(error.getErrorMessage()));
        }
    }
}
