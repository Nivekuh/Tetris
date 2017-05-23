package src;

import javafx.geometry.Pos;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;


public class Board
{
	static int queueLen = 5;

	public Rectangle[][] board; /*Row, Column*/
	private ArrayList<Tetromino> queue;
	private Tetromino currentPiece;
	private Main main;
	static int x = 10;
	static int y = 24;

	int level = 1;
	int linesCleared = 0;

	public static int[] getDimensions() {
		return new int[]{x, y};
	}

	public static long getDelay(int level) {
		return 1500/level;
	}

	public Board(int x, int y /*Dimensions*/, Main m){
		main = m;
		board = new Rectangle[y][x];
		queue = new ArrayList<Tetromino>();
		populateQueue();
		init();
	}

	public void init() {
		gameLoop();
	}

	public void populateQueue(){
		while(queue.size() < queueLen)
			queue.add(new Tetromino());
	}

	public void gameLoop() {
		drop();
		//level= level+1;

		Timer timer = new Timer();
		timer.schedule(new TimerTask() {
			public void run() {
				Platform.runLater(new Runnable() {
					public void run() {
						gameLoop();
					}
				});
			}
		}, getDelay(level));
	}

	public void drop(){
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
			for(Position pos : currentPiece.getLocations())
			{
				if(pos.x >= 0 && pos.y >= -1 && y < board.length-1 && board[y + 1][x] != null){
					canDrop = false;
					break;
				}
			}

			if (canDrop)
			{
				Rectangle[][] boardC = new Rectangle[board.length][board[0].length];
				System.arraycopy(board, 0, boardC, 0, board.length);

				for(Rectangle component : currentPiece.components) {
					int x = (int) (currentPiece.pos.x + component.getX());
					int y = (int) (currentPiece.pos.y + component.getY());

					if(x >= 0 && y >= -1) {
						boardC[y + 1][x] = component;
					}
				}
				currentPiece.move(0,1);

				main.updateDisplay(boardC);
			}
			else
			{
				for(Rectangle component : currentPiece.components)
					board[(int)(currentPiece.pos.y + component.getY() + 1)][(int)(currentPiece.pos.x + component.getX())] = component;
				currentPiece = null;

				main.updateDisplay(board);
			}
		}

	}
}