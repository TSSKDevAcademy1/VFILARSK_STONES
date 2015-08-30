import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import consoleUI.ConsoleUI;
import myCore.BestTimes;
import myCore.Field;
import myCore.FieldLoader;

public class TilesGame {

	public static void main(String[] args) {

		Field field = new Field(5, 5);
		BestTimes bestTimes = new BestTimes();

		FieldLoader fLoader = new FieldLoader();
	//	ConsoleUI ui = new ConsoleUI(bestTimes, fLoader);

		 ConsoleUI ui = new ConsoleUI(field,bestTimes,fLoader);

	}

}
