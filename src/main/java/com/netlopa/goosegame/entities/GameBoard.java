package com.netlopa.goosegame.entities;

import java.util.ArrayList;
import java.util.List;

public class GameBoard {

	private List<CellType> cells;
	private GameStatusEnum gameStatus;
	private List<Player> players;
	
	public List<CellType> getCells() {
		return cells;
	}
	public void setCells(List<CellType> cells) {
		this.cells = cells;
	}
	public GameStatusEnum getGameStatus() {
		return gameStatus;
	}
	public void setGameStatus(GameStatusEnum gameStatus) {
		this.gameStatus = gameStatus;
	}
	public List<Player> getPlayers() {
		return players;
	}
	public void setPlayers(List<Player> players) {
		this.players = players;
	}
	
	public GameBoard(List<CellType> cells) {
		this.cells = cells;
		this.gameStatus = GameStatusEnum.ADDING_PLAYERS;
		this.players = new ArrayList<Player>();
	}
	
	
}
