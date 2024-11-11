package org.esosa.spring.service.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de la lógica de negocio para interactuar con la API de Star Wars y obtener información sobre starships.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Service
public class MovieStarWarsStarshipService {
    private final RestTemplate restTemplate;

    public MovieStarWarsStarshipService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene una lista de starships de Star Wars para una página específica.
     *
     * @param page El número de página para obtener los resultados.
     * @return Una lista de objetos que representan a las starships de Star Wars.
     */
    public List<Object> getStarship(int page) {
        String url = "https://swapi.dev/api/starships/?page=" + page;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (List<Object>) response.getBody().get("results");
    }

    /**
     * Obtiene una starship específica de Star Wars por su ID.
     *
     * @param id El ID de la starship a obtener.
     * @return Un objeto que representa a la starship solicitada.
     */
    public Object getStarshipById(int id) {
        String url = "https://swapi.dev/api/starships/" + id + "/";
        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * Busca starships de Star Wars por su name.
     *
     * @param name El name de la starship a buscar.
     * @return Una lista de objetos que representan a las starships cuyo name coincide con la búsqueda.
     */
    public List<Object> getStarshipByName(String name) {
        String url = "https://swapi.dev/api/starships/?title=" + name;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (List<Object>) response.getBody().get("results");
    }
}
