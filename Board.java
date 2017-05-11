import java.util.ArrayList;

public class Board{
	private Piece[][] board; /*Row, Column*/
	private ArrayList<Tetromino> queue;
	
	public Board(int x, int y /*Dimensions*/){
		board = new Piece[y][x];
		queue = new ArrayList<Tetromino>();
	}
	
	public void addTetromino(Tetromino obj){
		
	}
	
	public boolean hasObjectAtPosition(Position pos){
		return false;
	}
	
	public void refresh(){
		
	}
}