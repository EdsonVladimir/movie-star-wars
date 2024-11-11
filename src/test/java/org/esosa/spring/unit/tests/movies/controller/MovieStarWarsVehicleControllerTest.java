package org.esosa.spring.unit.tests.movies.controller;

import org.esosa.spring.controller.movie.MovieStarWarsVehicleController;
import org.esosa.spring.service.movie.MovieStarWarsVehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MovieStarWarsVehicleControllerTest {

    @Mock
    private MovieStarWarsVehicleService movieStarWarsVehicleService;

    private MovieStarWarsVehicleController movieStarWarsVehicleController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        movieStarWarsVehicleController = new MovieStarWarsVehicleController(movieStarWarsVehicleService);
    }

    @Test
    void testGetVehicle_success() {
        // Arrange
        List<Object> mockVehicleList = Collections.singletonList(new Object());
        when(movieStarWarsVehicleService.getVehicle(1)).thenReturn(mockVehicleList);

        // Act
        ResponseEntity<List<Object>> response = movieStarWarsVehicleController.getVehicle(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockVehicleList, response.getBody());
        verify(movieStarWarsVehicleService, times(1)).getVehicle(1);
    }

    @Test
    void testGetVehicle_failure() {
        // Arrange
        when(movieStarWarsVehicleService.getVehicle(1)).thenThrow(new RuntimeException("Service error"));

        // Act
        ResponseEntity<List<Object>> response = movieStarWarsVehicleController.getVehicle(1);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Collections.singletonList("Service error"), response.getBody());
        verify(movieStarWarsVehicleService, times(1)).getVehicle(1);
    }

    @Test
    void testGetVehicleById_success() {
        // Arrange
        Object mockVehicle = new Object();
        when(movieStarWarsVehicleService.getVehicleById(1)).thenReturn(mockVehicle);

        // Act
        ResponseEntity<Object> response = movieStarWarsVehicleController.getVehicleById(1);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockVehicle, response.getBody());
        verify(movieStarWarsVehicleService, times(1)).getVehicleById(1);
    }

    @Test
    void testGetVehicleById_failure() {
        // Arrange
        when(movieStarWarsVehicleService.getVehicleById(1)).thenThrow(new RuntimeException("Service error"));

        // Act
        ResponseEntity<Object> response = movieStarWarsVehicleController.getVehicleById(1);

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Service error", response.getBody());
        verify(movieStarWarsVehicleService, times(1)).getVehicleById(1);
    }

    @Test
    void testGetVehicleBySearch_success() {
        // Arrange
        List<Object> mockVehicleList = Collections.singletonList(new Object());
        when(movieStarWarsVehicleService.getVehicleByName("X-Wing")).thenReturn(mockVehicleList);

        // Act
        ResponseEntity<List<Object>> response = movieStarWarsVehicleController.getVehicleBySearch("X-Wing");

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(mockVehicleList, response.getBody());
        verify(movieStarWarsVehicleService, times(1)).getVehicleByName("X-Wing");
    }

    @Test
    void testGetVehicleBySearch_failure() {
        // Arrange
        when(movieStarWarsVehicleService.getVehicleByName("X-Wing")).thenThrow(new RuntimeException("Service error"));

        // Act
        ResponseEntity<List<Object>> response = movieStarWarsVehicleController.getVehicleBySearch("X-Wing");

        // Assert
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals(Collections.singletonList("Service error"), response.getBody());
        verify(movieStarWarsVehicleService, times(1)).getVehicleByName("X-Wing");
    }
}