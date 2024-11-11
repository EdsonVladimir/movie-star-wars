package org.esosa.spring.unit.tests.movies.service;

import org.esosa.spring.service.movie.MovieStarWarsStarshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

public class MovieStarWarsStarshipServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MovieStarWarsStarshipService movieStarWarsStarshipService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks before each test
    }

    @Test
    public void testGetStarship() {
        // Given
        String url = "https://swapi.dev/api/starships/?page=1";
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("results", Arrays.asList("Starship 1", "Starship 2", "Starship 3"));
        ResponseEntity<Map> responseEntity = ResponseEntity.ok(responseMap);

        // When
        when(restTemplate.getForEntity(url, Map.class)).thenReturn(responseEntity);
        List<Object> starships = movieStarWarsStarshipService.getStarship(1);

        // Then
        assertNotNull(starships);
        assertEquals(3, starships.size());
        assertTrue(starships.contains("Starship 1"));
        verify(restTemplate, times(1)).getForEntity(url, Map.class);  // Verifying the interaction with RestTemplate
    }

    @Test
    public void testGetStarshipById() {
        // Given
        int id = 1;
        String url = "https://swapi.dev/api/starships/" + id + "/";
        Object starship = "Starship 1";
        when(restTemplate.getForObject(url, Object.class)).thenReturn(starship);

        // When
        Object result = movieStarWarsStarshipService.getStarshipById(id);

        // Then
        assertNotNull(result);
        assertEquals("Starship 1", result);
        verify(restTemplate, times(1)).getForObject(url, Object.class);
    }

    @Test
    public void testGetStarshipByName() {
        // Given
        String name = "Millennium Falcon";
        String url = "https://swapi.dev/api/starships/?title=" + name;
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("results", Arrays.asList("Millennium Falcon", "Millennium Falcon II"));
        ResponseEntity<Map> responseEntity = ResponseEntity.ok(responseMap);

        // When
        when(restTemplate.getForEntity(url, Map.class)).thenReturn(responseEntity);
        List<Object> starships = movieStarWarsStarshipService.getStarshipByName(name);

        // Then
        assertNotNull(starships);
        assertEquals(2, starships.size());
        assertTrue(starships.contains("Millennium Falcon"));
        verify(restTemplate, times(1)).getForEntity(url, Map.class);
    }
}