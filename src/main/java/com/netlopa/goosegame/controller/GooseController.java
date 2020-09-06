package com.netlopa.goosegame.controller;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

import com.netlopa.goosegame.entities.CellType;
import com.netlopa.goosegame.entities.GameBoard;
import com.netlopa.goosegame.entities.Player;
import com.netlopa.goosegame.exceptions.DuplicatePlayerException;
import com.netlopa.goosegame.exceptions.InvalidDiceCombinationException;
import com.netlopa.goosegame.exceptions.PlayerNotFoundException;

public class GooseController implements GooseControllerInterface {

	GameBoard gameBoard;

	public String addPlayer(String name) throws DuplicatePlayerException {

		List<Player> players = gameBoard.getPlayers();
		if (players.stream().anyMatch(item -> name.equalsIgnoreCase(item.getName()))) throw new DuplicatePlayerException();
		players.add(new Player(name));

		String output = "players: ";
		output = output + players.stream().map(item -> item.getName()).collect(Collectors.joining(", "));

		return output;
	}


	public String movePlayer(String name, int dice1, int dice2)
			throws InvalidDiceCombinationException, PlayerNotFoundException {

		List<Player> players = gameBoard.getPlayers();
		List<CellType> cells = gameBoard.getCells();
		Optional<Player> optPlayer = players.stream().filter(item -> item.getName().equalsIgnoreCase(name)).findFirst();
		if (!optPlayer.isPresent()) throw new PlayerNotFoundException();
		Player player = optPlayer.get();

		//Check if the dice values are allowed
		if (dice1 < 1 || dice1 > 6 || dice2 < 1 || dice2 > 6) throw new InvalidDiceCombinationException();

		int startingPosition = player.getPosition();
		int finalPosition = startingPosition + dice1 + dice2;
		int gameBoardLen = gameBoard.getCells().size();

		String result = name + " rolls " + dice1 + ", "+ dice2 + ". ";
		result = result + name + " moves from " + (startingPosition == 0 ? "Start" : startingPosition) + " to ";

		if (finalPosition == gameBoardLen-1) {
			// Win case
			result = result + finalPosition + ". " + name + " Wins!!";
		}
		else {
			if (finalPosition > gameBoardLen-1) {
				//Bounce case
				int diff = finalPosition - (gameBoardLen-1);
				finalPosition = (gameBoardLen-1) - diff;
				result = result + (gameBoardLen-1) + ". " + name + " bounces! " + name + " returns to ";
			}

			//Check the cell type
			if (CellType.BRIDGE.equals(cells.get(finalPosition))) {
				finalPosition = 12;
				result = result + "The Bridge. " + name + " jumps to ";
			}
			if (CellType.GOOSE.equals(cells.get(finalPosition))) {
				result = result + finalPosition + ", The Goose. " + name + " moves again and goes to ";
				finalPosition = finalPosition + dice1 + dice2;
				if (CellType.GOOSE.equals(cells.get(finalPosition))) {
					result = result + finalPosition + ", The Goose. " + name + " moves again and goes to ";
					finalPosition = finalPosition + dice1 + dice2;
				}

			}
			if (CellType.NORMAL.equals(cells.get(finalPosition))) {
				result = result + finalPosition;
			}
		}

		//I check if there is another player on the final position cell
		final int positionToFind = finalPosition;
		Optional<Player> optPrankedPlayer = players.stream().filter(item -> item.getPosition() == positionToFind).findFirst();
		if (optPrankedPlayer.isPresent()) {
			Player prankedPlayer = optPrankedPlayer.get(); 
			result = result + ". On " + finalPosition + " there is " + prankedPlayer.getName() + ", who returns to " + startingPosition;
			prankedPlayer.setPosition(startingPosition);
		}
		
		player.setPosition(finalPosition);
		
		return result;
	}

	public String movePlayer(String name) throws PlayerNotFoundException, InvalidDiceCombinationException {
		
		//System make random dice roll		
		int r1 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		int r2 = ThreadLocalRandom.current().nextInt(1, 6 + 1);
		
		return this.movePlayer(name,r1,r2);
	}

	public GooseController(List<CellType> cells) {
		this.gameBoard = new GameBoard(cells);
	}

}
