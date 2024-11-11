package org.esosa.spring.unit.tests.movies.controller;

import org.esosa.spring.controller.movie.MovieStarWarsStarshipController;
import org.esosa.spring.service.movie.MovieStarWarsStarshipService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieStarWarsStarshipControllerTest {

    @Mock
    private MovieStarWarsStarshipService movieStarWarsStarshipService;

    private MovieStarWarsStarshipController movieStarWarsStarshipController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieStarWarsStarshipController = new MovieStarWarsStarshipController(movieStarWarsStarshipService);
    }

    @Test
    void testGetStarship_success() {
        // Arrange
        List<Object> mockStarshipList = Collections.singletonList(new Object());
        when(movieStarWarsStarshipService.getStarship(1)).thenReturn(mockStarshipList);

        // Act
        ResponseEntity<List<Object>> response = movieStarWarsStarshipController.getStarship(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockStarshipList, response.getBody());
        verify(movieStarWarsStarshipService, times(1)).getStarship(1);
    }

    @Test
    void testGetStarship_failure() {
        // Arrange
        when(movieStarWarsStarshipService.getStarship(1)).thenThrow(new RuntimeException("Service error"));

        // Act
        ResponseEntity<List<Object>> response = movieStarWarsStarshipController.getStarship(1);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Collections.singletonList("Service error"), response.getBody());
        verify(movieStarWarsStarshipService, times(1)).getStarship(1);
    }

    @Test
    void testGetStarshipById_success() {
        // Arrange
        Object mockStarship = new Object();
        when(movieStarWarsStarshipService.getStarshipById(1)).thenReturn(mockStarship);

        // Act
        ResponseEntity<Object> response = movieStarWarsStarshipController.getStarshipById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockStarship, response.getBody());
        verify(movieStarWarsStarshipService, times(1)).getStarshipById(1);
    }

    @Test
    void testGetStarshipById_failure() {
        // Arrange
        when(movieStarWarsStarshipService.getStarshipById(1)).thenThrow(new RuntimeException("Service error"));

        // Act
        ResponseEntity<Object> response = movieStarWarsStarshipController.getStarshipById(1);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Service error", response.getBody());
        verify(movieStarWarsStarshipService, times(1)).getStarshipById(1);
    }

    @Test
    void testGetStarshipBySearch_success() {
        // Arrange
        List<Object> mockStarshipList = Collections.singletonList(new Object());
        when(movieStarWarsStarshipService.getStarshipByName("Falcon")).thenReturn(mockStarshipList);

        // Act
        ResponseEntity<List<Object>> response = movieStarWarsStarshipController.getStarshipBySearch("Falcon");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockStarshipList, response.getBody());
        verify(movieStarWarsStarshipService, times(1)).getStarshipByName("Falcon");
    }

    @Test
    void testGetStarshipBySearch_failure() {
        // Arrange
        when(movieStarWarsStarshipService.getStarshipByName("Falcon")).thenThrow(new RuntimeException("Service error"));

        // Act
        ResponseEntity<List<Object>> response = movieStarWarsStarshipController.getStarshipBySearch("Falcon");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Collections.singletonList("Service error"), response.getBody());
        verify(movieStarWarsStarshipService, times(1)).getStarshipByName("Falcon");
    }
}