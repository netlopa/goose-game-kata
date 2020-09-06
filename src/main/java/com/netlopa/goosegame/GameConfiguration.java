package com.netlopa.goosegame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import com.netlopa.goosegame.entities.CellType;

public class GameConfiguration {

	public static List<CellType> getGameConfiguration() {
		List<CellType> output = new ArrayList<CellType>();
		
		// Goose (spaces 5, 9, 14, 18, 23, 27)
		int[] gooseCells = new int[] { 5, 9, 14, 18, 23, 27 };
		
		// The first cell (index = 0) is the START cell that it's not counted on the all the other cells of the game
		for(int i=0;i<=63;i++) {
			
			final int idx = i;
			
			// Bridge (space 6)
			if (i==6) {
				output.add(CellType.BRIDGE);
			}
			else {
				if (Arrays.stream(gooseCells).anyMatch(item -> idx == item)) {
					output.add(CellType.GOOSE);
				}
				else {
					output.add(CellType.NORMAL);
				}
			}
		}
		
		return output;
	}
}
