package org.esosa.spring.unit.tests.movies.controller;

import org.esosa.spring.controller.movie.MovieStarWarsFilmController;
import org.esosa.spring.service.movie.MovieStarWarsFilmService;
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

public class MovieStarWarsFilmControllerTest {

    @Mock
    private MovieStarWarsFilmService movieStarWarsFilmService;

    @InjectMocks
    private MovieStarWarsFilmController movieStarWarsFilmController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);  // Initialize mocks before each test
    }

    @Test
    public void testGetFilm() {
        // Given
        int page = 1;
        List<Object> films = Collections.singletonList("Film 1");
        when(movieStarWarsFilmService.getFilm(page)).thenReturn(films);

        // When
        ResponseEntity<List<Object>> response = movieStarWarsFilmController.getFilm(page);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().contains("Film 1"));
        verify(movieStarWarsFilmService, times(1)).getFilm(page);
    }

    @Test
    public void testGetFilmById() {
        // Given
        int id = 1;
        Object film = "Film 1";
        when(movieStarWarsFilmService.getFilmById(id)).thenReturn(film);

        // When
        ResponseEntity<Object> response = movieStarWarsFilmController.getFilmById(id);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Film 1", response.getBody());
        verify(movieStarWarsFilmService, times(1)).getFilmById(id);
    }

    @Test
    public void testGetFilmBySearch() {
        // Given
        String search = "Star Wars";
        List<Object> films = Collections.singletonList("Star Wars: A New Hope");
        when(movieStarWarsFilmService.getFilmByName(search)).thenReturn(films);

        // When
        ResponseEntity<List<Object>> response = movieStarWarsFilmController.getFilmBySearch(search);

        // Then
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(1, response.getBody().size());
        assertTrue(response.getBody().contains("Star Wars: A New Hope"));
        verify(movieStarWarsFilmService, times(1)).getFilmByName(search);
    }

    @Test
    public void testGetFilm_InternalServerError() {
        // Given
        int page = 1;
        when(movieStarWarsFilmService.getFilm(page)).thenThrow(new RuntimeException("Service error"));

        // When
        ResponseEntity<List<Object>> response = movieStarWarsFilmController.getFilm(page);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Service error"));
        verify(movieStarWarsFilmService, times(1)).getFilm(page);
    }

    @Test
    public void testGetFilmById_InternalServerError() {
        // Given
        int id = 1;
        when(movieStarWarsFilmService.getFilmById(id)).thenThrow(new RuntimeException("Service error"));

        // When
        ResponseEntity<Object> response = movieStarWarsFilmController.getFilmById(id);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertEquals("Service error", response.getBody());
        verify(movieStarWarsFilmService, times(1)).getFilmById(id);
    }

    @Test
    public void testGetFilmBySearch_InternalServerError() {
        // Given
        String search = "Star Wars";
        when(movieStarWarsFilmService.getFilmByName(search)).thenThrow(new RuntimeException("Service error"));

        // When
        ResponseEntity<List<Object>> response = movieStarWarsFilmController.getFilmBySearch(search);

        // Then
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Service error"));
        verify(movieStarWarsFilmService, times(1)).getFilmByName(search);
    }
}
