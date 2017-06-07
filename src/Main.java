package src;

import com.sun.javafx.font.directwrite.RECT;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

import java.awt.*;
import java.util.ArrayList;

public class Main extends Application {
    final int tileSize = 20;
    int[] dimensions;

    //General
    private Stage stage;
    private Group root;
    //Board
    private GridPane boardGrid;
    private GridPane queueGrid;
    private GridPane scoreGrid;
    private Board board;
    private Label label;
    //Menu
    private Button start;

    public int gameState = 0; //Menu, Playing, Game Over/Paused

    public void start(Stage primaryStage) throws Exception{
        dimensions = Board.getDimensions();

        root = new Group();
        root.getStyleClass().add("base");
        stage = primaryStage;
        stage.setTitle("Tetris");
        stage.setScene(new Scene(root, (tileSize+1)*(dimensions[0]+7), (tileSize+1)*dimensions[1]));
        stage.getScene().getStylesheets().add("src/style.css");
        stage.show();

        setupMenu();
        setupEventHandlers();
    }

    public void setupEventHandlers(){
        Main m = this;

        stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent event) {
                System.out.println("Key Pressed: " + event.getCode());
                if(gameState == 1){
                    int tX = 0;
                    int tY = 0;

                    if(event.getCode().equals(KeyCode.LEFT))
                        tX = -1;
                    else if(event.getCode().equals(KeyCode.RIGHT))
                        tX = 1;
                    else if(event.getCode().equals(KeyCode.DOWN))
                        tY = 1;
                    else if(event.getCode().equals(KeyCode.Z) && board.currentPiece.canRotate(board.board, board.currentPiece.pos))
                        board.currentPiece.rotate();
                    else if(event.getCode().equals(KeyCode.X) && board.currentPiece.canRotate(board.board, board.currentPiece.pos))
                        board.currentPiece.rotate();

                    if(board.currentPiece.canMoveToPosition(board.board, new Position(tX + board.currentPiece.pos.x, tY + board.currentPiece.pos.y)))
                        board.currentPiece.move(tX, tY);
                    board.currentPiece.putInBounds(board.board);
                    board.setDisplay();
                }
            }
        });

        start.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                if(gameState == 0) {
                    setupGameBoard();
                    board = new Board(dimensions[0], dimensions[1], m);
                }
            }
        });
    }

    public void setupMenu(){
        gameState = 0;

        start = new Button("Start");
        start.setPrefWidth(.5*stage.getScene().getWidth());
        start.setLayoutX(.25*(stage.getScene().getWidth()));
        start.setLayoutY(.5*(stage.getScene().getHeight() - start.getHeight()));
        root.getChildren().add(start);
    }

    public void setupGameBoard(){
        GridPane gameGrid = new GridPane();
        GridPane sidebarGridR = new GridPane();
        scoreGrid = new GridPane();
        boardGrid = new GridPane();
        queueGrid = new GridPane();

        label = new Label("");
        label.setLayoutX(stage.getScene().getWidth()/2);
        label.setLayoutX(stage.getScene().getHeight()/2);

        queueGrid.getStyleClass().add("sidebar");
        scoreGrid.getStyleClass().add("sidebar");
        boardGrid.getStyleClass().add("board");
        gameGrid.getStyleClass().add("game");

        sidebarGridR.add(queueGrid, 0, 1);
        gameGrid.add(scoreGrid, 0, 0);
        gameGrid.add(boardGrid, 1, 0);
        gameGrid.add(sidebarGridR, 2, 0);
        root.getChildren().add(gameGrid);
        root.getChildren().add(label);

        for (int k = 0; k < dimensions[0]; k++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0/dimensions[0]);
            boardGrid.getColumnConstraints().add(cc);
        }

        gameState = 1;
    }

    public void setGameOverlay(String text, int state){
        gameState = state;
        label.setText(text);
    }

    public void updateGridPane(GridPane gridPane, Rectangle[][] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                Rectangle p = arr[i][j];

                if(p != null) {
                    Rectangle t = new Rectangle(0, 0, tileSize, tileSize);
                    t.setFill(p.getFill());
                    gridPane.add(t, j, i);
                }
                else{
                    final Region grid = new Region();
                    grid.setPrefWidth(tileSize);
                    grid.setPrefHeight(tileSize);
                    gridPane.add(grid, j, i);
                }
            }
        }
    }

    public GridPane queueRegion(Tetromino piece){
        GridPane region = new GridPane();
        region.getStyleClass().add("queue");

        for(int i = 0; i < 3; i++){
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0/3);
            region.getColumnConstraints().add(cc);
        }

        Rectangle[][] arr = new Rectangle[4][3];
        piece.putInBounds(arr);
        piece.addToArray(arr);

        for(int i = 3; i > 0; i--) {
            boolean canRemove = true;
            for (Rectangle rect : arr[i]) {
                if (rect != null) {
                    canRemove = false;
                    break;
                }
            }

            if(canRemove){
                arr = new Rectangle[i][3];
                piece.putInBounds(arr);
                piece.addToArray(arr);
            }
        }

        updateGridPane(region, arr);

        return region;
    }

    public void updateDisplay(Rectangle[][] arr, Board board){
        ArrayList<Tetromino> queue = board.queue;
        boardGrid.getChildren().clear();
        queueGrid.getChildren().clear();
        scoreGrid.getChildren().clear();

        updateGridPane(boardGrid, arr);
        for(int i = 0; i < queue.size(); i++)
            queueGrid.add(queueRegion(queue.get(i)), 0, i);
        scoreGrid.add(new Label("Score: " + String.format("%05d", board.score)), 0, 0);
        scoreGrid.add(new Label("Level: " + board.level), 0, 1);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
