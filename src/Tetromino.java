package src;

import java.lang.Math;
import javafx.scene.shape.*;

public class Tetromino{
	public static String[] Blocks = {"I", "J", "L", "O", "Z", "T", "S"};

	public Rectangle[] components;
	public Position pos;

	public Tetromino(){
		String nextBlock = Blocks[(int)(Math.random()*7)];
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

	public void rotate(){
		for(Rectangle component : components){
			double x = component.getX();
			component.setX(component.getY());
			component.setY(-x);
		}
	}
}