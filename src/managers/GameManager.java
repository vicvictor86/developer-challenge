package src.managers;

import javax.swing.*;
import java.awt.*;

public class GameManager {
    private final int screenHeight;
    private final int screenWidth;
    private int clickQuantity = 0;
    private int seconds = 0;

    private Thread coutingThread;

    public BoardManager boardManager;

    public GameManager(int boardSize) {
        this.boardManager = new BoardManager(boardSize);

        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();

        screenHeight = Math.min(boardManager.getBoardSize() * 100, (int) width);
        screenWidth = Math.min(boardManager.getBoardSize() * 100, (int) height);
    }

    public int getClickQuantity() {
        return this.clickQuantity;
    }

    public int getScreenHeight() {
        return this.screenHeight;
    }

    public int getScreenWidth() {
        return this.screenWidth;
    }

    public void restartGame() {
        clickQuantity = 0;
    }

    public void addClick() {
        clickQuantity++;
    }

    public void startCounting(JLabel timeLabel) {
        coutingThread = new Thread(() -> {
            while (true) {
                try {
                    Thread.sleep(1000);
                    this.seconds++;
                    timeLabel.setText("Tempo: " + this.seconds + " segs");
                    System.out.println(this.seconds);
                } catch (InterruptedException e) {
                    break;
                }
            }
        });
        coutingThread.start();
    }

    public void stopCounting() {
        this.coutingThread.interrupt();
        this.seconds = 0;
    }

}
