package src;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Controller {
    public static int x = 10;
    public static int y = 24;
    private static Board board;

    public static int[] getDimensions(){
        return new int[]{x, y};
    }

    public static void main(String[] args)
    {
        board = new Board(x, y);
    }

    public static void gameLoop()
    {
    }

    public static void Drop()
    {

    }

    public static void move()
    {

    }
}
