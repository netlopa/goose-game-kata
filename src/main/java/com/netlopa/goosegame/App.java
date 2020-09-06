package com.netlopa.goosegame;

import java.util.List;
import java.util.Scanner;

import com.netlopa.goosegame.controller.GooseController;
import com.netlopa.goosegame.controller.GooseControllerInterface;
import com.netlopa.goosegame.entities.CellType;

/**
 * Hello world!
 *
 */
public class App 
{
	public static void main( String[] args )
	{

		List<CellType> cells = GameConfiguration.getGameConfiguration();
		GooseControllerInterface controller = new GooseController(cells);

		System.out.println("Welcome in the Goose Game");
		Scanner scanner = new Scanner(System.in);

		while(scanner.hasNextLine()) {
			String command = scanner.nextLine();
			String result = controller.runCommand(command);
			System.out.println(result);
			if (result.contains("Wins") || result.contains("Bye")) break;
		}

		scanner.close();


	}
}
