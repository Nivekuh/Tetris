package src;

import java.util.ArrayList;
import java.awt.Rectangle;

public class Board{
	private Rectangle[][] board; /*Row, Column*/
	private ArrayList<Tetromino> queue;
	
	public Board(int x, int y /*Dimensions*/){
		board = new Rectangle[y][x];
		queue = new ArrayList<Tetromino>();
	}
	
	public boolean hasObjectAtPosition(Position pos){
		return false;
	}
	
	public void refresh(){
		
	}
}