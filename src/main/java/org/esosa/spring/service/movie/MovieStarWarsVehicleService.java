package org.esosa.spring.service.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de la lógica de negocio para interactuar con la API de Star Wars y obtener información sobre vehicles.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Service
public class MovieStarWarsVehicleService {
    private final RestTemplate restTemplate;

    public MovieStarWarsVehicleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene una lista de vehicles de Star Wars para una página específica.
     *
     * @param page El número de página para obtener los resultados.
     * @return Una lista de objetos que representan a las vehicles de Star Wars.
     */
    public List<Object> getVehicle(int page) {
        String url = "https://swapi.dev/api/vehicles/?page=" + page;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (List<Object>) response.getBody().get("results");
    }

    /**
     * Obtiene una vehicle específica de Star Wars por su ID.
     *
     * @param id El ID de la vehicle a obtener.
     * @return Un objeto que representa a la vehicle solicitada.
     */
    public Object getVehicleById(int id) {
        String url = "https://swapi.dev/api/vehicles/" + id + "/";
        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * Busca vehicles de Star Wars por su name.
     *
     * @param name El name de la vehicle a buscar.
     * @return Una lista de objetos que representan a las vehicles cuyo name coincide con la búsqueda.
     */
    public List<Object> getVehicleByName(String name) {
        String url = "https://swapi.dev/api/vehicles/?title=" + name;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (List<Object>) response.getBody().get("results");
    }
}
