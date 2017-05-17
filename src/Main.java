package src;

import javafx.application.Application;
import javafx.stage.*;
import javafx.scene.*;
import javafx.scene.shape.*;
import javafx.event.*;
import javafx.scene.input.*;
import javafx.scene.paint.*;

public class Main extends Application {
    private Stage stage;
    private Group root;
    private Group board;

    private int tileSize = 20;
    private int padding = 20;

    @Override
    public void start(Stage primaryStage) throws Exception{
        int[] dimensions = Controller.getDimensions();
        root = new Group();
        stage = primaryStage;
        stage.setTitle("Damn");
        stage.setScene(new Scene(root, dimensions[0]*tileSize + 2*padding, dimensions[1]*tileSize + 2*padding));
        Rectangle rectBoard = new Rectangle(padding, padding, dimensions[0]*tileSize, dimensions[1]*tileSize);
        rectBoard.setFill(Color.GHOSTWHITE);
        board = new Group(rectBoard);
        root.getChildren().add(board);
        stage.show();

        setUpEventHandlers();
    }

    public void setUpEventHandlers(){
        stage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
            public void handle(KeyEvent ke) {
                System.out.println("Key Pressed: " + ke.getCode());
            }
        });
    }

    public void updateDisplay(Rectangle[][] arr){
        for(int i = 0; i < arr.length; i++){
            for(int j = 0; j < arr[i].length; j++){
                Rectangle p = arr[i][j];
                p.setX(padding+j*tileSize);
                p.setY(padding+i*tileSize);
                board.getChildren().add(p);
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
