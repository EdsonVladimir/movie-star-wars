package org.esosa.spring.service.movie;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

/**
 * Servicio encargado de la lógica de negocio para interactuar con la API de Star Wars y obtener información sobre personas.
 *
 * @author Edson Sosa
 * @version 1.0
 * @since 2024
 */
@Service
public class MovieStarWarsPeopleService {
    private final RestTemplate restTemplate;

    /**
     * Constructor para inyectar la dependencia de RestTemplate.
     *
     * @param restTemplate El RestTemplate para realizar las solicitudes HTTP a la API de Star Wars.
     */
    public MovieStarWarsPeopleService(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /**
     * Obtiene una lista de personas de Star Wars para una página específica.
     *
     * @param page El número de página para obtener los resultados.
     * @return Una lista de objetos que representan a las personas de Star Wars.
     */
    public List<Object> getPeople(int page) {
        String url = "https://swapi.dev/api/people/?page=" + page;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (List<Object>) response.getBody().get("results");
    }

    /**
     * Obtiene una persona específica de Star Wars por su ID.
     *
     * @param id El ID de la persona a obtener.
     * @return Un objeto que representa a la persona solicitada.
     */
    public Object getPeopleById(int id) {
        String url = "https://swapi.dev/api/people/" + id + "/";
        return restTemplate.getForObject(url, Object.class);
    }

    /**
     * Busca personas de Star Wars por su nombre.
     *
     * @param name El nombre de la persona a buscar.
     * @return Una lista de objetos que representan a las personas cuyo nombre coincide con la búsqueda.
     */
    public List<Object> getPeopleByName(String name) {
        String url = "https://swapi.dev/api/people/?name=" + name;
        ResponseEntity<Map> response = restTemplate.getForEntity(url, Map.class);
        return (List<Object>) response.getBody().get("results");
    }
}
