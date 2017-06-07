package src;

import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.shape.Rectangle;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
import javafx.application.Platform;


public class Board
{
	static int queueLen = 5;

	public Rectangle[][] board; /*Row, Column*/
	public ArrayList<Tetromino> queue;
	public ArrayList<Tetromino> hold;
	public Tetromino currentPiece;
	private Main main;
	static int x = 10;
	static int y = 24;
	boolean isPaused = false;
	boolean gameOver = false;

	int level = 1;
	int score = 0;
	int linesCleared = 0;
	int streak = 0;

	public static int[] getDimensions()
	{
		return new int[]{x, y};
	}

	public static long getDelay(int level)
	{
		return 500/level;
	}

	public Board(int x, int y /*Dimensions*/, Main m)
	{
		main = m;
		board = new Rectangle[y][x];
		queue = new ArrayList<Tetromino>();
		hold = new ArrayList<Tetromino>();
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

	public int getScore(int linesCleared, int level, int streak)
	{
		int score = 0;
		if(linesCleared == 1)
		{
			score += 50 * (level + 1);
			streak = 0;
		}
		if(linesCleared == 2)
		{
			score += 150 * (level +1);
			streak = 0;
		}
		if(linesCleared == 3)
		{
			score += 350 * (level +1);
			streak = 0;
		}
		if(linesCleared == 4)
		{
			score += 1000 * (level + 1) + (streak * 4000 * (level + 1));
			streak++;
		}

		return score;
	}

	public void gameLoop() {
		if(!isPaused && !gameOver) {
			int roundLinesCleared = drop();
			linesCleared += roundLinesCleared;
			score += getScore(roundLinesCleared, level, streak);
			if (roundLinesCleared > 0 && linesCleared % 20 == 0)
				level++;
		}

		Timer timer = new Timer();
		timer.schedule(new TimerTask()
		{
			public void run()
			{
				Platform.runLater(new Runnable() {
					public void run() {
						gameLoop();
					}
				});
			}
		}, getDelay(level));
	}

	public void pause()
	{
		if(!isPaused) {
			isPaused = true;
			main.setGameOverlay("Paused", 2);
		}
		else {
			isPaused = false;
			main.setGameOverlay("", 2);
		}
	}

	public int drop(){
		int roundLinesCleared = 0;

		if (currentPiece == null)
		{
			System.out.println("New Piece");
			currentPiece = queue.get(0);
			currentPiece.pos.x = 5;
			currentPiece.pos.y = -2;
			queue.remove(0);
			populateQueue();

			for(Position pos : currentPiece.getLocations())
			{
				if(pos.y > -1 && pos.y < board.length && board[pos.y][pos.x] != null){
					gameOver = true;
					main.setGameOverlay("Game Over", 2);
					break;
				}
			}
		}

		boolean canDrop = true;
		for(Position pos : currentPiece.getLocations())
		{
			if(pos.y > -1 && !(pos.y < board.length-1 && board[pos.y + 1][pos.x] == null)){
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
			currentPiece.addToArray(board);
			setDisplay();
			roundLinesCleared = checkLines(currentPiece.getLocations());
			currentPiece = null;
		}

		return roundLinesCleared;
	}

	public void setDisplay()
	{
		Rectangle[][] boardC = new Rectangle[board.length][board[0].length];
		for(int i = 0; i < board.length; i++){
			for(int j = 0; j < board[i].length; j++)
				boardC[i][j] = board[i][j];
		}

		currentPiece.addToArray(boardC);
		main.updateDisplay(boardC, this);
	}

	public int checkLines(Position[] posArr)
	{
		int roundLinesCleared = 0;

		for(Position pos : posArr){
			int check = 0;
			for(Rectangle rect : board[pos.y]
					)
			{
				if(rect != null)
					check++;
			}

			if(check == x) {
				for(int i = pos.y; i > 0; i--)
				{
					for(int j = 0; j < board[i].length; j++)
					{
						board[i][j] = board[i-1][j];
					}
				}
				roundLinesCleared++;
			}
		}

		return roundLinesCleared;
	}

	public void Hold()
	{
		boolean canHold = true;
		if (canHold)
		{
			hold.add(currentPiece);
			canHold = false;
		}

		else if(!canHold)
		{
			Tetromino tempPiece = new Tetromino();
			tempPiece = hold.get(0);
			hold.set(0, currentPiece);
			currentPiece = tempPiece;
		}
	}
}

