package org.esosa.spring.unit.tests.movies.service;

import org.esosa.spring.service.movie.MovieStarWarsFilmService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MovieStarWarsFilmServiceTest {

    @Mock
    private RestTemplate restTemplate;  // Mock de RestTemplate

    @InjectMocks
    private MovieStarWarsFilmService movieStarWarsFilmService;  // Inyección de dependencias del servicio

    private Map<String, Object> mockResponse;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);  // Inicializa los mocks
        mockResponse = new HashMap<>();
    }

    @Test
    void testGetFilm() {
        // Preparación: simulamos la respuesta de la API para la página 1
        List<Object> mockFilms = new ArrayList<>();
        mockFilms.add(new Object()); // Simulamos una película en la respuesta

        mockResponse.put("results", mockFilms);
        ResponseEntity<Map> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.getForEntity("https://swapi.dev/api/films/?page=1", Map.class))
                .thenReturn(responseEntity);

        // Ejecución: obtenemos los films
        List<Object> films = movieStarWarsFilmService.getFilm(1);

        // Verificación: verificamos que la lista de films no sea nula y que contenga 1 film
        assertNotNull(films);
        assertEquals(1, films.size());

        // Verificamos que el método getForEntity se haya llamado correctamente
        verify(restTemplate, times(1)).getForEntity("https://swapi.dev/api/films/?page=1", Map.class);
    }

    @Test
    void testGetFilmById() {
        // Preparación: simulamos la respuesta de la API para una película específica con ID 1
        Object mockFilm = new Object(); // Simulamos una película

        when(restTemplate.getForObject("https://swapi.dev/api/films/1/", Object.class))
                .thenReturn(mockFilm);

        // Ejecución: obtenemos la película por ID
        Object film = movieStarWarsFilmService.getFilmById(1);

        // Verificación: verificamos que el objeto film no sea nulo
        assertNotNull(film);

        // Verificamos que el método getForObject se haya llamado correctamente
        verify(restTemplate, times(1)).getForObject("https://swapi.dev/api/films/1/", Object.class);
    }

    @Test
    void testGetFilmByName() {
        // Preparación: simulamos la respuesta de la API para una búsqueda de película por nombre
        List<Object> mockFilms = new ArrayList<>();
        mockFilms.add(new Object()); // Simulamos una película en la respuesta

        mockResponse.put("results", mockFilms);
        ResponseEntity<Map> responseEntity = ResponseEntity.ok(mockResponse);
        when(restTemplate.getForEntity("https://swapi.dev/api/films/?title=Starwars", Map.class))
                .thenReturn(responseEntity);

        // Ejecución: obtenemos los films por nombre
        List<Object> films = movieStarWarsFilmService.getFilmByName("Starwars");

        // Verificación: verificamos que la lista de films no sea nula y que contenga 1 film
        assertNotNull(films);
        assertEquals(1, films.size());

        // Verificamos que el método getForEntity se haya llamado correctamente
        verify(restTemplate, times(1)).getForEntity("https://swapi.dev/api/films/?title=Starwars", Map.class);
    }
}
