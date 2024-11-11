package org.esosa.spring.controller.movie;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.esosa.spring.service.movie.MovieStarWarsVehicleService;
import org.esosa.spring.util.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

/**
 * Controlador encargado de manejar todas las operaciones relacionadas con las vehicles de Star Wars.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Api(tags = "Movie Star Wars Vehicle API", value = "Funcionalidades para listar Vehicle", description = "Operaciones relacionadas a vehicle")
@RestController
@RequestMapping("/api/starwars")
public class MovieStarWarsVehicleController {
    private final MovieStarWarsVehicleService movieStarWarsVehicleService;

    public MovieStarWarsVehicleController(MovieStarWarsVehicleService movieStarWarsVehicleService) {
        this.movieStarWarsVehicleService = movieStarWarsVehicleService;
    }

    /**
     * Listar vehicles por página.
     * Este método devuelve una lista de vehicles paginada.
     *
     * @param page El número de la página a recuperar.
     * @return ResponseEntity que contiene la lista de vehicles.
     */
    @ApiOperation(value = "Listar Vehicle por pagina", notes = "Devuelve lista de Vehicles por pagina")
    @GetMapping("/vehicle")
    public ResponseEntity<List<Object>> getVehicle(@RequestParam(defaultValue = "1") int page) {
        try {
            List<Object> vehicle = movieStarWarsVehicleService.getVehicle(page);
            return ResponseEntity.ok(vehicle);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(error.getErrorMessage()));
        }
    }

    /**
     * Obtener una vehicle específica por ID.
     * Este método obtiene una vehicle filtrada por su ID.
     *
     * @param id El ID de la vehicle a recuperar.
     * @return ResponseEntity que contiene el objeto de la vehicle.
     */
    @ApiOperation(value = "Filtrar Vehicle por id", notes = "Devuelve Vehicle filtrado por Id")
    @GetMapping("/vehicle/{id}")
    public ResponseEntity<Object> getVehicleById(@PathVariable int id) {
        try {
            Object person = movieStarWarsVehicleService.getVehicleById(id);
            return ResponseEntity.ok(person);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error.getErrorMessage());
        }
    }

    /**
     * Buscar vehicles por nombre.
     * Este método filtra a las vehicles según el nombre proporcionado.
     *
     * @param search El nombre de la vehicle a buscar.
     * @return ResponseEntity que contiene la lista de vehicles que coinciden con la búsqueda.
     */
    @ApiOperation(value = "Filtrar Vehicle por nombre", notes = "Devuelve Vehicles filtrado por nombre")
    @GetMapping("/vehicle/search")
    public ResponseEntity<List<Object>> getVehicleBySearch(@RequestParam String search) {
        try {
            List<Object> vehicle = movieStarWarsVehicleService.getVehicleByName(search);
            return ResponseEntity.ok(vehicle);
        } catch (Exception e) {
            ErrorResponse error = new ErrorResponse("USER_CREATION_ERROR", e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(Collections.singletonList(error.getErrorMessage()));
        }
    }
}
