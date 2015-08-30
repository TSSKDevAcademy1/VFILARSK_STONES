package myCore;

import java.io.Serializable;

public class Tile implements Serializable {

	private int positionX = 0;
	private int positionY = 0;
	private int value = 0;
	
	
	public Tile(int positionX, int positionY,int value){
		this.positionX = positionX;
		this.positionY = positionY;
		this.value = value;
	}
	
	public int getPositionX() {
		return positionX;
	}
	public void setPositionX(int positionX) {
		this.positionX = positionX;
	}
	public int getPositionY() {
		return positionY;
	}
	public void setPositionY(int positionY) {
		this.positionY = positionY;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}
}
