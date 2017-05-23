package src;

import javafx.scene.shape.Rectangle;
import org.w3c.dom.css.Rect;

import java.awt.*;
import java.util.ArrayList;
import javafx.scene.shape.*;

public class Board
{
	static int queueLen = 5;

	public Rectangle[][] board; /*Row, Column*/
	private ArrayList<Tetromino> queue;
	private Tetromino currentPiece;

	public Board(int x, int y /*Dimensions*/){
		board = new Rectangle[y][x];
		queue = new ArrayList<Tetromino>();
		populateQueue();
	}

	public void populateQueue(){
		while(queue.size() < queueLen)
			queue.add(new Tetromino());
	}

	public void cycle(){
		//If currentPiece = nil, add a piece to the board from top of queue, populateQueue();
		//Else drop piece down (Check if can move down)
		//Add components of currentPiece to copy of board array
		//Send board array to the main to render it
		if (currentPiece == null)
		{
			currentPiece = queue.get(0);
			populateQueue();
		}
		else
		{
			boolean canDrop = true;
			for(Rectangle component : currentPiece.components)
			{
				if(board[(int)(currentPiece.pos.y + component.getY() + 1)][(int)(currentPiece.pos.x + component.getX())] != null){
					canDrop = false;
					break;
				}
			}

			if (canDrop)
			{
				Rectangle[][] boardC = new Rectangle[board.length][board[0].length];
				for (int i = 0; i < board.length; i++)
				{
					for (int j = 0; j < board[i].length; j++)
						boardC[i][j] = board[i][j];
				}

				for(Rectangle component : currentPiece.components)
					boardC[(int)(currentPiece.pos.y + component.getY() + 1)][(int)(currentPiece.pos.x + component.getX())] = component;

				//Main.updateDisplay(boardC);
			}
			else
			{
				for(Rectangle component : currentPiece.components){
					board[(int)(currentPiece.pos.y + component.getY() + 1)][(int)(currentPiece.pos.x + component.getX())] = component;
				}
			}
		}

	}
}