package src;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import java.util.TimerTask;
import java.util.Timer;

public class Controller {
    static int x = 10;
    static int y = 24;
    private static Board board;
    static int level = 1;

    public static int[] getDimensions(){
        return new int[]{x, y};
    }

    public static void init(){
        board = new Board(x, y);
        gameLoop();
    }

    public static long getDelay(int level){
        return 1500/level;
    }

    public static void gameLoop()
    {
        cycle();
        level= level+1;

        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                gameLoop();
            }
        }, getDelay(level));
    }
}
