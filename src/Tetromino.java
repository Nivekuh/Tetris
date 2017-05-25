package src;

import java.lang.Math;
import javafx.scene.shape.Rectangle;

public class Tetromino{
	public static String[] Blocks = {"I", "J", "L", "O", "Z", "T", "S"};

	public Rectangle[] components;
	public Position pos;

	public Tetromino(){
		String nextBlock = Blocks[(int)(Math.random()*7)];
		pos = new Position(Board.getDimensions()[0]/2, 0);
		components = new Rectangle[4];

		components[0] = new Rectangle(0, 0, 0, 0);
		switch (nextBlock){
			case "I":
				components[1] = new Rectangle(0, 1, 0, 0);
				components[2] = new Rectangle(0, 2, 0, 0);
				components[3] = new Rectangle(0, 3, 0, 0);
				break;
			case "J":
				components[1] = new Rectangle(-1, 0, 0, 0);
				components[2] = new Rectangle(0, 1, 0, 0);
				components[3] = new Rectangle(0, 2, 0, 0);
				break;
			case "L":
				components[1] = new Rectangle(1, 0, 0, 0);
				components[2] = new Rectangle(0, 1, 0, 0);
				components[3] = new Rectangle(0, 2, 0, 0);
				break;
			case "O":
				components[1] = new Rectangle(1, 0, 0, 0);
				components[2] = new Rectangle(2, 1, 0, 0);
				components[3] = new Rectangle(2, 0, 0, 0);
				break;
			case "Z":
				components[1] = new Rectangle(-1, 1, 0, 0);
				components[2] = new Rectangle(0, 1, 0, 0);
				components[3] = new Rectangle(1, 0, 0, 0);
				break;
			case "T":
				components[1] = new Rectangle(-1, 0, 0, 0);
				components[2] = new Rectangle(0, -1, 0, 0);
				components[3] = new Rectangle(1, 0, 0, 0);
				break;
			case "S":
				components[1] = new Rectangle(-1, 0, 0, 0);
				components[2] = new Rectangle(-1, -1, 0, 0);
				components[3] = new Rectangle(1, 0, 0, 0);
				break;
		}
	}

	public Position[] getLocations(){
		Position[] posArr = new Position[4];

		for(int i = 0; i < components.length; i++) {
			posArr[i] = new Position((int) (components[i].getX() + pos.x), (int) (components[i].getY() + pos.y));
			//System.out.println("x: " + posArr[i].x + ", y: " + posArr[i].y);
		}

		return posArr;
	}

	public void rotate(){
		for(Rectangle component : components){
			double x = component.getX();
			component.setX(component.getY());
			component.setY(-x);
		}
	}

	public Rectangle[][] addToArray(Rectangle[][] board){
		for(Rectangle component : components) {
			int x = (int) (pos.x + component.getX());
			int y = (int) (pos.y + component.getY());

			if(x >= 0 && y >= 0) {
				board[y][x] = component;
			}
		}

		return board;
	}

	public void move(int x, int y){ //Relative
		pos.x += x;
		pos.y += y;
	}
}