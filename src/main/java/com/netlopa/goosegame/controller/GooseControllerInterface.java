package com.netlopa.goosegame.controller;

import com.netlopa.goosegame.exceptions.DuplicatePlayerException;
import com.netlopa.goosegame.exceptions.InvalidDiceCombinationException;
import com.netlopa.goosegame.exceptions.PlayerNotFoundException;

public interface GooseControllerInterface {

	public String addPlayer(String name) throws DuplicatePlayerException;
	public String movePlayer(String name, int dice1, int dice2) throws InvalidDiceCombinationException, PlayerNotFoundException;
	public String movePlayer(String name) throws PlayerNotFoundException;
}
