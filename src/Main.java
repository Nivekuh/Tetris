package src;

import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
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
    private Board board;
    //Menu
    private Button start;

    private int gameState = 0; //Menu, Playing, Game Over/Paused

    public void start(Stage primaryStage) throws Exception{
        dimensions = Board.getDimensions();

        root = new Group();
        root.getStyleClass().add("base");
        stage = primaryStage;
        stage.setTitle("Tetris");
        stage.setScene(new Scene(root, (tileSize+1)*(dimensions[0]+3), (tileSize+1)*dimensions[1]));
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
                    if(event.getCode().equals(KeyCode.LEFT))
                        board.currentPiece.move(-1, 0);
                    else if(event.getCode().equals(KeyCode.RIGHT))
                        board.currentPiece.move(1, 0);
                    else if(event.getCode().equals(KeyCode.DOWN))
                        board.currentPiece.move(0, 1);
                    else if(event.getCode().equals(KeyCode.Z))
                        board.currentPiece.rotate();
                    else if(event.getCode().equals(KeyCode.X))
                        board.currentPiece.rotate();

                    board.currentPiece.putInBounds(board.board);
                    board.setDisplay();
                }
            }
        });

        stage.getScene().setOnMouseReleased(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent event) {
                System.out.println("Mouse clicked: " + event.getPickResult());
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
        boardGrid = new GridPane();
        queueGrid = new GridPane();
        queueGrid.getStyleClass().add("queue");
        boardGrid.getStyleClass().add("board");
        boardGrid.setLayoutX(boardGrid.getWidth());
        root.getChildren().add(boardGrid);
        root.getChildren().add(queueGrid);

        for (int k = 0; k < dimensions[0]; k++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0/dimensions[0]);
            boardGrid.getColumnConstraints().add(cc);
            queueGrid.getColumnConstraints().add(cc);
        }

        updateDisplay(new Rectangle[dimensions[1]][dimensions[0]], new ArrayList<Tetromino>());
        gameState = 1;
    }

    public GridPane queueRegion(Tetromino piece){
        GridPane region = new GridPane();

        for(int i = 0; i < 3; i++){
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0/dimensions[0]);
            region.getColumnConstraints().add(cc);
        }

        /*piece.putInBounds(new Rectangle[4][3]);
        for(Rectangle component : piece.components)
            region.add(component, (int)component.getX(), (int)component.getY());*/

        return region;
    }

    public void updateDisplay(Rectangle[][] arr, ArrayList<Tetromino> queue){
        boardGrid.getChildren().clear();

        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                Rectangle p = arr[i][j];
                System.out.print("Piece: " + p + " ");

                if(p != null) {
                    boardGrid.add(new Rectangle(0, 0, tileSize, tileSize), j, i);
                }
                else{
                    final Region grid = new Region();
                    grid.setPrefWidth(tileSize);
                    grid.setPrefHeight(tileSize);
                    boardGrid.add(grid, j, i);
                }
            }
        }

        for(int i = 0; i < queue.size(); i++)
            queueGrid.add(queueRegion(queue.get(i)), 0, i);

        System.out.println();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
