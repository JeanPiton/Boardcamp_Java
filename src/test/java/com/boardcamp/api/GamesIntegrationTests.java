package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GameRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
class GamesIntegrationTests {
    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private GameRepository gameRepository;

    @BeforeEach
    @AfterEach
    void cleanUpDatabase() {
        gameRepository.deleteAll();
    }

    @Test
	void givenRepeatedName_whenCreatingGame_thenThrowsError() {
		GameDTO gameDTO = new GameDTO("test","test",1,1);
		GameModel gameConflict = new GameModel(gameDTO);
        gameRepository.save(gameConflict);
        HttpEntity<GameDTO> body = new HttpEntity<>(gameDTO);
		
        ResponseEntity<String> response = restTemplate.exchange("/games", HttpMethod.POST,body,String.class);

		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
        assertEquals(1, gameRepository.count());
	}

    @Test
	void givenValidGame_whenCreatingGame_thenThrowsError() {
		GameDTO gameDTO = new GameDTO("test","test",1,1);
        HttpEntity<GameDTO> body = new HttpEntity<>(gameDTO);
		
        ResponseEntity<String> response = restTemplate.exchange("/games", HttpMethod.POST,body,String.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(1, gameRepository.count());
	}
}
