package consoleUI;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;

import myCore.BestTimes;
import myCore.Field;
import myCore.FieldLoader;
import myCore.GameState;
import myCore.WrongInputException;



public class ConsoleUI implements Serializable {

	GameState gameState;
	Field field;
	BestTimes bestTimes;
	FieldLoader fLoader;
	int emptyRow = 0;
	int emptyColumn = 0;
	private BufferedReader input = new BufferedReader(new InputStreamReader(System.in));
	

	
	public ConsoleUI(Field field,BestTimes bestTimes,FieldLoader fLoader) {
		gameState = GameState.PLAYING;
		if(fLoader.load() == null){
			this.field = field;
		} else {
			this.field = fLoader.load();
		}
		this.bestTimes = bestTimes;
		this.fLoader = fLoader;
		update();
	}
	
	public ConsoleUI(BestTimes bestTimes,FieldLoader fLoader){
		gameState = GameState.PLAYING;
			this.field = fLoader.load();
		this.bestTimes = bestTimes;
		this.fLoader = fLoader;
			update();
	}
	
	public ConsoleUI(Field field,BestTimes bestTimes){
		gameState = GameState.PLAYING;
			this.field = field;
		this.bestTimes = bestTimes;
		this.fLoader = new FieldLoader();
			update();
	}
	
	
	 /**
     * Communication with player.
	 * @throws WrongInputException 
     */
	public void processInput() throws WrongInputException {
		System.out.println("Vyber volbu: new pre novu hru,save pre ulozenie,exit pre ukoncenie,best pre najlepsie casy, w s a d pre ovladanie");
		String line;
		try {
			line = input.readLine();
			if((line == "new" || line == "exit" || line == "w" || line == "s" || line == "a" || line == "d" || line == "w" || line == "w")){
				throw new WrongInputException();
			}
			switch(line){
			case "new": newGame();break;
			case "exit": exit();break;
			case "w": moveUp(field);break;
			case "a": moveLeft();break;
			case "s": moveDown(field);break;
			case "d": moveRight();break;
			case "best": bestTimes();break;
			case "save": save();break;
			default: System.out.println("Zadal si nespravny vstup");
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void bestTimes(){
		System.out.println(bestTimes.toString());
	}
	
	public void newGame(){
		new ConsoleUI(new Field(field.getRowCount(),field.getRowCount()),bestTimes);
	}
	
	public void exit(){
		System.exit(0);
	}
	
	public void save() throws IOException{
		fLoader.save(field);
	}
	
	 /**
     * Make move in field.
     * @param field field
     */
	public void moveUp(Field field) {
		isEmpty();
		if (emptyRow + 1 <= field.getRowCount() -1) {
			field.setTile(emptyRow, emptyColumn, field.getTile(emptyRow + 1, emptyColumn).getValue());
			field.setTile(emptyRow + 1, emptyColumn, 99);

		}
	}
	
	public void moveDown(Field field) {
		isEmpty();
		if (emptyRow - 1 >= 0) {
			field.setTile(emptyRow, emptyColumn, field.getTile(emptyRow - 1, emptyColumn).getValue());
			field.setTile(emptyRow - 1, emptyColumn, 99);

		}
	}
	
	public void moveRight(){
		isEmpty();
		if (emptyColumn - 1 >= 0) {
			field.setTile(emptyRow, emptyColumn, field.getTile(emptyRow, emptyColumn-1).getValue());
			field.setTile(emptyRow, emptyColumn-1, 99);

		}
	}
	
	public void moveLeft(){
		isEmpty();
		if (emptyColumn + 1 <= field.getRowCount()-1) {
			field.setTile(emptyRow, emptyColumn, field.getTile(emptyRow, emptyColumn + 1).getValue());
			field.setTile(emptyRow, emptyColumn + 1, 99);

		}
	}
	
	public void update() {
		long time = System.currentTimeMillis();
		while (true) {
			if (isSucessfull()) {
				gameState = GameState.SOLVED;
			}
			if (gameState.equals(GameState.PLAYING)) {
				System.out.println("Uspech: " + isSucessfull());
				System.out.println("Tvoj cas je: " + (System.currentTimeMillis() - time) / 1000 + " sekund");
				field.vypis();
				try {
					processInput();
				} catch (WrongInputException e) {
					System.err.println("Zadal si zly vstup");
					e.printStackTrace();
				}
			} else if (gameState.equals(GameState.SOLVED)) {
				time = (System.currentTimeMillis() - time) / 1000;
				int intTime = (int) time;
				System.out.println("Zadaj svoje meno");
				try {
					String playerName = input.readLine();
					bestTimes.addPlayerTime(playerName, intTime);
					System.exit(0);
				} catch (IOException e) {
					System.err.println("Nepodarilo sa nacitat meno");
					e.printStackTrace();
				}
			}
		}
	}
	
	public boolean isSucessfull() {
		boolean bool = true;
		int actValue = 0;
		for (int r = 0; r < field.getRowCount(); r++) {
			for (int c = 0; c < field.getColumnCount(); c++) {
				if(actValue > field.getTile(r, c).getValue()){
					bool = false;
				}
				actValue = field.getTile(r, c).getValue();
			}
		}
		return bool;
	}
	
	public void isEmpty(){
		for (int r = 0; r < field.getRowCount(); r++) {
			for (int c = 0; c < field.getColumnCount(); c++) {
				if (field.getTile(r, c).getValue() == 99) {
					emptyRow = field.getTile(r, c).getPositionX();
					emptyColumn = field.getTile(r, c).getPositionY();
					System.out.printf("Pozicia: " + emptyRow + emptyColumn + "\n");
				}
			}
		}
	}
	
}

