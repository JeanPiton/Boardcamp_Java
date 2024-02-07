package com.boardcamp.api;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;

import com.boardcamp.api.dtos.GameDTO;
import com.boardcamp.api.exceptions.GameConflictException;
import com.boardcamp.api.models.GameModel;
import com.boardcamp.api.repositories.GameRepository;
import com.boardcamp.api.services.GameService;

@SpringBootTest
class GamesUnitTests {

	@InjectMocks
	private GameService gameService;

	@Mock
	private GameRepository gameRepository;

	@Test
	void givenRepeatedName_whenCreatingGame_thenThrowsError() {
		GameDTO gameDTO = new GameDTO("test","test",1,1);
		doReturn(true).when(gameRepository).existsByName(any());

		GameConflictException exception = assertThrows(GameConflictException.class, () -> gameService.save(gameDTO));

		assertNotNull(exception);
		assertEquals("This game alredy exists", exception.getMessage());
		verify(gameRepository, times(0)).save(any());
	}

	@Test
	void givenValidGame_whenCreatingGame_thenThrowsError() {
		GameDTO gameDTO = new GameDTO("Test","test",1,1);
		GameModel newGame = new GameModel(gameDTO);
		doReturn(false).when(gameRepository).existsByName(any());
		doReturn(newGame).when(gameRepository).save(any());

		GameModel result = gameService.save(gameDTO);

		assertNotNull(result);
		verify(gameRepository, times(1)).existsByName(any());
		verify(gameRepository, times(1)).save(any());
		assertEquals(result, newGame);
	}

}
