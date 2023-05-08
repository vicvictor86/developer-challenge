package src.Managers;

import java.awt.*;

public class GameManager {
    private final int screenHeight;
    private final int screenWidth;
    private int clickQuantity = 0;

    public GameManager(int boardSize) {
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        screenHeight = Math.min(boardSize * 100, (int) width);
        screenWidth = Math.min(boardSize * 100, (int) height);
    }

    public int getClickQuantity() {
        return clickQuantity;
    }

    public int getScreenHeight() {
        return screenHeight;
    }

    public int getScreenWidth() {
        return screenWidth;
    }

    public void restartGame() {
        clickQuantity = 0;
    }

    public void addClick() {
        clickQuantity++;
    }

    public void startCouting() {
        Thread thread = new Thread(() -> {
            int seconds = 0;
            while (true) {
                try {
                    Thread.sleep(1000);
                    seconds++;
                    System.out.println(seconds);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        thread.start();
    }

}
