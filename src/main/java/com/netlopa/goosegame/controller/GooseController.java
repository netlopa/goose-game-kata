package com.netlopa.goosegame.controller;

import java.util.List;

import com.netlopa.goosegame.entities.CellType;
import com.netlopa.goosegame.entities.GameBoard;
import com.netlopa.goosegame.exceptions.DuplicatePlayerException;
import com.netlopa.goosegame.exceptions.InvalidDiceCombinationException;
import com.netlopa.goosegame.exceptions.PlayerNotFoundException;

public class GooseController implements GooseControllerInterface {
	
	GameBoard gameBoard;

	public String addPlayer(String name) throws DuplicatePlayerException {
		// TODO Auto-generated method stub
		return null;
	}

	public String movePlayer(String name, int dice1, int dice2)
			throws InvalidDiceCombinationException, PlayerNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	public String movePlayer(String name) throws PlayerNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
	
	public GooseController(List<CellType> cells) {
		this.gameBoard = new GameBoard(cells);
	}

}
