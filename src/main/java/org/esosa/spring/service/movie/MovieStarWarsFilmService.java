package org.esosa.spring.service.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de la lógica de negocio para interactuar con la API de Star Wars y obtener información sobre films.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Service
public class MovieStarWarsFilmService {
    private final RestTemplate restTemplate;

    /**
     * Constructor para inyectar la dependencia de RestTemplate.
     *
     * @param restTemplate El RestTemplate para realizar las solicitudes HTTP a la API de Star Wars.
     */
    public MovieStarWarsFilmService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene una lista de films de Star Wars para una página específica.
     *
     * @param page El número de página para obtener los resultados.
     * @return Una lista de objetos que representan a las films de Star Wars.
     */
    public List<Object> getFilm(int page) {
        String url = "https://swapi.dev/api/films/?page=" + page;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (List<Object>) response.getBody().get("results");
    }

    /**
     * Obtiene una film específica de Star Wars por su ID.
     *
     * @param id El ID de la film a obtener.
     * @return Un objeto que representa a la film solicitada.
     */
    public Object getFilmById(int id) {
        String url = "https://swapi.dev/api/films/" + id + "/";
        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * Busca films de Star Wars por su titulo.
     *
     * @param name El titulo de la film a buscar.
     * @return Una lista de objetos que representan a las films cuyo titulo coincide con la búsqueda.
     */
    public List<Object> getFilmByName(String name) {
        String url = "https://swapi.dev/api/films/?title=" + name;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (List<Object>) response.getBody().get("results");
    }
}
