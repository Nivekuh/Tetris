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
			System.out.println("New Piece");
			currentPiece = queue.get(0);
			queue.remove(0);
			currentPiece.move(0, -1);
			populateQueue();
		}
		else
		{
			boolean canDrop = true;
			for(Position pos : currentPiece.getLocations())
			{
				if(!(pos.y < board.length-1 && board[pos.y + 1][pos.x] == null)){
					canDrop = false;
					break;
				}
			}

			if (canDrop)
			{
				currentPiece.move(0, 1);
				setDisplay();
			}
			else
			{
				setDisplay();
				checkLines(currentPiece.getLocations());
				currentPiece = null;
			}
		}
	}

	public void setDisplay(){
		Rectangle[][] boardC = new Rectangle[board.length][board[0].length];
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++)
				boardC[i][j] = board[i][j];
		}

		currentPiece.addToArray(boardC);
		main.updateDisplay(boardC);
	}

	public void checkLines(Position[] posArr){
		for(Position pos : posArr){
			int check = 0;
			for(Rectangle rect : board[pos.y]){
				if(rect != null)
					check++;
			}

			if(check == x) {
				for(int i = pos.y; i > 0; i--){
					for(int j = 0; j < board[i].length; j++){
						board[i][j] = board[i-1][j];
					}
				}
			}
		}
	}
}