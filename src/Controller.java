package sample;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

public class Controller {
    public static int x = 10;
    public static int y = 24;

    public static int[] getDimensions(){
        return new int[]{x, y};
    }
}
