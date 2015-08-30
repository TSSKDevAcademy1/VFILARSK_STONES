package myCore;

public class WrongInputException extends Exception{
	public WrongInputException(){
		System.err.println("Zadal si nespravny vstup");
	}
}
