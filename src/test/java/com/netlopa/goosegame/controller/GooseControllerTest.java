package com.netlopa.goosegame.controller;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.netlopa.goosegame.GameConfiguration;
import com.netlopa.goosegame.entities.CellType;
import com.netlopa.goosegame.exceptions.DuplicatePlayerException;
import com.netlopa.goosegame.exceptions.InvalidDiceCombinationException;
import com.netlopa.goosegame.exceptions.PlayerNotFoundException;

public class GooseControllerTest {

	List<CellType> cells;
	@Before
	public void setUp() throws Exception {
		cells = GameConfiguration.getGameConfiguration();
	}

	@Test
	public void testAddPlayer() throws DuplicatePlayerException {

		GooseControllerInterface controller = new GooseController(cells);
		String result = controller.addPlayer("Pippo");
		assertEquals("players: Pippo", result);
		String result2 = controller.addPlayer("Pluto");
		assertEquals("players: Pippo, Pluto", result2);
	}

	@Test(expected = DuplicatePlayerException.class)
	public void testAddPlayerDuplicatePlayerException() throws DuplicatePlayerException {

		GooseControllerInterface controller = new GooseController(cells);
		String result = controller.addPlayer("Pippo");
		assertEquals("players: Pippo",result);
		controller.addPlayer("Pippo");

	}

	@Test
	public void testMovePlayerDiceOk() throws DuplicatePlayerException, InvalidDiceCombinationException, PlayerNotFoundException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		String result = controller.movePlayer("Pippo", 4, 3);
		assertEquals("Pippo rolls 4, 3. Pippo moves from Start to 7",result);
	}

	@Test(expected = InvalidDiceCombinationException.class)
	public void testMovePlayerDiceInvalid() throws DuplicatePlayerException, InvalidDiceCombinationException, PlayerNotFoundException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		controller.movePlayer("Pippo", 8, 2);
	}

	@Test(expected = PlayerNotFoundException.class)
	public void testMovePlayerWithPlayerNotFound() throws DuplicatePlayerException, InvalidDiceCombinationException, PlayerNotFoundException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.movePlayer("Pippo", 3, 2);
	}

	@Test
	public void testMovePlayerWithPrank() throws DuplicatePlayerException, InvalidDiceCombinationException, PlayerNotFoundException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		controller.addPlayer("Pluto");
		controller.movePlayer("Pippo", 4, 3); // So it goes in cell 7
		controller.movePlayer("Pluto", 4, 4); // So it goes in cell 8
		controller.movePlayer("Pippo", 1, 2); // So it goes in cell 10
		String result = controller.movePlayer("Pluto",1, 1); //It goes in cell 10 but there is the prank
		assertEquals("Pluto rolls 1, 1. Pluto moves from 8 to 10. On 10 there is Pippo, who returns to 8",result);
	}

	@Test
	public void testMovePlayerWithBridge() throws DuplicatePlayerException, InvalidDiceCombinationException, PlayerNotFoundException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		String result = controller.movePlayer("Pippo", 3, 3);
		assertEquals("Pippo rolls 3, 3. Pippo moves from Start to The Bridge. Pippo jumps to 12",result);
	}

	@Test
	public void testMovePlayerWithGooseSingleJump() throws DuplicatePlayerException, InvalidDiceCombinationException, PlayerNotFoundException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		String result = controller.movePlayer("Pippo", 3, 2);
		assertEquals("Pippo rolls 3, 2. Pippo moves from Start to 5, The Goose. Pippo moves again and goes to 10",result);
	}

	@Test
	public void testMovePlayerWithGooseDoubleJump() throws DuplicatePlayerException, InvalidDiceCombinationException, PlayerNotFoundException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		controller.movePlayer("Pippo", 6, 4);
		String result = controller.movePlayer("Pippo", 2, 2);
		assertEquals("Pippo rolls 2, 2. Pippo moves from 10 to 14, The Goose. Pippo moves again and goes to 18, The Goose. Pippo moves again and goes to 22",result);
	}

	@Test(expected = PlayerNotFoundException.class)
	public void testMovePlayerRandomDicePlayerNotFoundException() throws DuplicatePlayerException, PlayerNotFoundException, InvalidDiceCombinationException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		controller.movePlayer("Pluto");
	}
	
	@Test
	public void testMovePlayerWithWin() throws DuplicatePlayerException, InvalidDiceCombinationException, PlayerNotFoundException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		controller.movePlayer("Pippo",6,6);
		controller.movePlayer("Pippo",6,6);
		controller.movePlayer("Pippo",6,6);
		controller.movePlayer("Pippo",6,6);
		controller.movePlayer("Pippo",6,6); // 60
		
		String result = controller.movePlayer("Pippo", 1, 2);
		assertEquals("Pippo rolls 1, 2. Pippo moves from 60 to 63. Pippo Wins!!",result);
	}
	
	@Test
	public void testMovePlayerWithBounce() throws DuplicatePlayerException, InvalidDiceCombinationException, PlayerNotFoundException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		controller.movePlayer("Pippo",6,6);
		controller.movePlayer("Pippo",6,6);
		controller.movePlayer("Pippo",6,6);
		controller.movePlayer("Pippo",6,6);
		controller.movePlayer("Pippo",6,6); // 60
		
		String result = controller.movePlayer("Pippo", 3, 2);
		assertEquals("Pippo rolls 3, 2. Pippo moves from 60 to 63. Pippo bounces! Pippo returns to 61",result);
	}

	@Test
	public void testMovePlayerRandomDice() throws DuplicatePlayerException, PlayerNotFoundException, InvalidDiceCombinationException {
		GooseControllerInterface controller = new GooseController(cells);
		controller.addPlayer("Pippo");
		String result = controller.movePlayer("Pippo");

		//I need to check if the dice are in the admittable values
		String[] tokens = result.split(" ");
		String token1 = tokens[2];
		String token2 = tokens[3];
		token1 = token1.replace(",", "");
		token2 = token2.replace(".", "");

		int dice1 = Integer.parseInt(token1);
		int dice2 = Integer.parseInt(token2);

		assertTrue(dice1 >= 1 && dice1 <= 6 && dice2 >= 1 && dice2 <= 6);

	}

}
