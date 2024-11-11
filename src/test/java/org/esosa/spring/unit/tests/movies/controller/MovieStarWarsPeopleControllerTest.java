package org.esosa.spring.unit.tests.movies.controller;

import org.esosa.spring.controller.movie.MovieStarWarsPeopleController;
import org.esosa.spring.service.movie.MovieStarWarsPeopleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

class MovieStarWarsPeopleControllerTest {

    @Mock
    private MovieStarWarsPeopleService movieStarWarsPeopleService;

    @InjectMocks
    private MovieStarWarsPeopleController movieStarWarsPeopleController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks before each test
    }

    @Test
    public void testGetPeople() {
        // Given
        int page = 1;
        List<Object> people = Collections.singletonList("People 1");
        when(movieStarWarsPeopleService.getPeople(page)).thenReturn(people);

        // When
        ResponseEntity<List<Object>> response = movieStarWarsPeopleController.getPeople(page);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().contains("People 1"));
        verify(movieStarWarsPeopleService, times(1)).getPeople(page);
    }

    @Test
    public void testGetPeopleById() {
        // Given
        int id = 1;
        Object film = "People 1";
        when(movieStarWarsPeopleService.getPeopleById(id)).thenReturn(film);

        // When
        ResponseEntity<Object> response = movieStarWarsPeopleController.getPeopleById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("People 1", response.getBody());
        verify(movieStarWarsPeopleService, times(1)).getPeopleById(id);
    }

    @Test
    public void testGetPeopleBySearch() {
        // Given
        String search = "Star Wars";
        List<Object> people = Collections.singletonList("Star Wars: A New Hope");
        when(movieStarWarsPeopleService.getPeopleByName(search)).thenReturn(people);

        // When
        ResponseEntity<List<Object>> response = movieStarWarsPeopleController.getPeopleBySearch(search);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().contains("Star Wars: A New Hope"));
        verify(movieStarWarsPeopleService, times(1)).getPeopleByName(search);
    }

    @Test
    public void testGetPeople_InternalServerError() {
        // Given
        int page = 1;
        when(movieStarWarsPeopleService.getPeople(page)).thenThrow(new RuntimeException("Service error"));

        // When
        ResponseEntity<List<Object>> response = movieStarWarsPeopleController.getPeople(page);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Service error"));
        verify(movieStarWarsPeopleService, times(1)).getPeople(page);
    }

    @Test
    public void testGetPeopleById_InternalServerError() {
        // Given
        int id = 1;
        when(movieStarWarsPeopleService.getPeopleById(id)).thenThrow(new RuntimeException("Service error"));

        // When
        ResponseEntity<Object> response = movieStarWarsPeopleController.getPeopleById(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Service error", response.getBody());
        verify(movieStarWarsPeopleService, times(1)).getPeopleById(id);
    }

    @Test
    public void testGetPeopleBySearch_InternalServerError() {
        // Given
        String search = "Star Wars";
        when(movieStarWarsPeopleService.getPeopleByName(search)).thenThrow(new RuntimeException("Service error"));

        // When
        ResponseEntity<List<Object>> response = movieStarWarsPeopleController.getPeopleBySearch(search);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Service error"));
        verify(movieStarWarsPeopleService, times(1)).getPeopleByName(search);
    }
}
