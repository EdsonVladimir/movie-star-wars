package org.esosa.spring.unit.tests.movies.service;

import org.esosa.spring.service.movie.MovieStarWarsVehicleService;
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

public class MovieStarWarsVehicleServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MovieStarWarsVehicleService movieStarWarsVehicleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks before each test
    }

    @Test
    public void testGetVehicle() {
        // Given
        String url = "https://swapi.dev/api/vehicles/?page=1";
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("results", Arrays.asList("Vehicle 1", "Vehicle 2", "Vehicle 3"));
        ResponseEntity<Map> responseEntity = ResponseEntity.ok(responseMap);

        // When
        when(restTemplate.getForEntity(url, Map.class)).thenReturn(responseEntity);
        List<Object> vehicles = movieStarWarsVehicleService.getVehicle(1);

        // Then
        assertNotNull(vehicles);
        assertEquals(3, vehicles.size());
        assertTrue(vehicles.contains("Vehicle 1"));
        verify(restTemplate, times(1)).getForEntity(url, Map.class);  // Verifying the interaction with RestTemplate
    }

    @Test
    public void testGetVehicleById() {
        // Given
        int id = 1;
        String url = "https://swapi.dev/api/vehicles/" + id + "/";
        Object vehicle = "Vehicle 1";
        when(restTemplate.getForObject(url, Object.class)).thenReturn(vehicle);

        // When
        Object result = movieStarWarsVehicleService.getVehicleById(id);

        // Then
        assertNotNull(result);
        assertEquals("Vehicle 1", result);
        verify(restTemplate, times(1)).getForObject(url, Object.class);
    }

    @Test
    public void testGetVehicleByName() {
        // Given
        String name = "Speeder Bike";
        String url = "https://swapi.dev/api/vehicles/?title=" + name;
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("results", Arrays.asList("Speeder Bike", "Speeder Bike II"));
        ResponseEntity<Map> responseEntity = ResponseEntity.ok(responseMap);

        // When
        when(restTemplate.getForEntity(url, Map.class)).thenReturn(responseEntity);
        List<Object> vehicles = movieStarWarsVehicleService.getVehicleByName(name);

        // Then
        assertNotNull(vehicles);
        assertEquals(2, vehicles.size());
        assertTrue(vehicles.contains("Speeder Bike"));
        verify(restTemplate, times(1)).getForEntity(url, Map.class);
    }
}