package org.esosa.spring.unit.tests.movies.service;

import org.esosa.spring.service.movie.MovieStarWarsPeopleService;
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

public class MovieStarWarsPeopleServiceTest {

    @Mock
    private RestTemplate restTemplate;

    @InjectMocks
    private MovieStarWarsPeopleService movieStarWarsPeopleService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks before each test
    }

    @Test
    public void testGetPeople() {
        // Given
        String url = "https://swapi.dev/api/people/?page=1";
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("results", Arrays.asList("Person 1", "Person 2", "Person 3"));
        ResponseEntity<Map> responseEntity = ResponseEntity.ok(responseMap);

        // When
        when(restTemplate.getForEntity(url, Map.class)).thenReturn(responseEntity);
        List<Object> people = movieStarWarsPeopleService.getPeople(1);

        // Then
        assertNotNull(people);
        assertEquals(3, people.size());
        assertTrue(people.contains("Person 1"));
        verify(restTemplate, times(1)).getForEntity(url, Map.class);  // Verifying the interaction with RestTemplate
    }

    @Test
    public void testGetPeopleById() {
        // Given
        int id = 1;
        String url = "https://swapi.dev/api/people/" + id + "/";
        Object person = "Person 1";
        when(restTemplate.getForObject(url, Object.class)).thenReturn(person);

        // When
        Object result = movieStarWarsPeopleService.getPeopleById(id);

        // Then
        assertNotNull(result);
        assertEquals("Person 1", result);
        verify(restTemplate, times(1)).getForObject(url, Object.class);
    }

    @Test
    public void testGetPeopleByName() {
        // Given
        String name = "Luke";
        String url = "https://swapi.dev/api/people/?name=" + name;
        Map<String, Object> responseMap = new HashMap<>();
        responseMap.put("results", Arrays.asList("Luke Skywalker", "Luke Skywalker Clone"));
        ResponseEntity<Map> responseEntity = ResponseEntity.ok(responseMap);

        // When
        when(restTemplate.getForEntity(url, Map.class)).thenReturn(responseEntity);
        List<Object> people = movieStarWarsPeopleService.getPeopleByName(name);

        // Then
        assertNotNull(people);
        assertEquals(2, people.size());
        assertTrue(people.contains("Luke Skywalker"));
        verify(restTemplate, times(1)).getForEntity(url, Map.class);
    }
}
