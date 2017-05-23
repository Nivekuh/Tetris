package src;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;

public class Main extends Application {
    final int tileSize = 20;
    int[] dimensions;

    //General
    private Stage stage;
    private Group root;
    //Board
    private GridPane boardGrid;
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
        stage.setScene(new Scene(root, tileSize*dimensions[0], tileSize*dimensions[1]));
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
        boardGrid.getStyleClass().add("board");
        root.getChildren().add(boardGrid);

        for (int k = 0; k < dimensions[0]; k++) {
            ColumnConstraints cc = new ColumnConstraints();
            cc.setPercentWidth(100.0/dimensions[0]);
            boardGrid.getColumnConstraints().add(cc);
        }

        updateDisplay(new Rectangle[dimensions[1]][dimensions[0]]);
        gameState = 1;
    }

    public void updateDisplay(Rectangle[][] arr){
        boardGrid.getChildren().clear();

        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                Rectangle p = arr[i][j];
                System.out.print("Piece: " + p + " ");

                if(p != null) {
                    p.setWidth(tileSize);
                    p.setHeight(tileSize);
                    p.setX(0);
                    p.setY(0);
                    boardGrid.add(p, j, i);
                }
                else{
                    final Region grid = new Region();
                    grid.setPrefWidth(tileSize);
                    grid.setPrefHeight(tileSize);
                    boardGrid.add(grid, j, i);
                }
            }
        }

        System.out.println();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
