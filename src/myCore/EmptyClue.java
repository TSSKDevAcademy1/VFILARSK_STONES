package myCore;

import java.io.Serializable;

public class EmptyClue extends Tile implements Serializable {


	public EmptyClue(int positionX, int positionY,int value) {
		super(positionX, positionY,value);
		// TODO Auto-generated constructor stub
	}
	

	public String toString() {

		Integer number = (Integer) getValue();
		return number.toString();

	}
	

}
