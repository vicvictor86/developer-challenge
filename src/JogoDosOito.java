package src;

import src.Managers.BoardManager;
import src.Managers.BoardPosition;
import src.Managers.GameManager;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JogoDosOito extends JFrame implements KeyListener {

    private final BoardManager boardManager = new BoardManager();
    private final GameManager gameManager = new GameManager(boardManager.getBoardSize());
    private final JButton[][] buttons = new JButton[boardManager.getBoardSize()][boardManager.getBoardSize()];

    public JogoDosOito() {
        super("Jogo dos Oito");

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(gameManager.getScreenWidth(), gameManager.getScreenHeight());
        setLayout(new GridLayout(boardManager.getBoardSize() + 1, boardManager.getBoardSize()));

        gameManager.startCouting();

        for (int i = 0; i < boardManager.getBoardSize(); i++) {
            for (int j = 0; j < boardManager.getBoardSize(); j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 36));
                button.addActionListener(e -> onClick(button));

                buttons[i][j] = button;
                add(button);
            }
        }

        JButton restartButton = new JButton("Reiniciar");
        restartButton.addActionListener(e -> restartGame());
        addRestartButton(restartButton);

        addKeyListener(this);
        setFocusable(true);
        updateBoard();
        setVisible(true);
    }

    private void addRestartButton(JButton restartButton) {
        if (boardManager.getBoardSize() % 2 != 0) {
            for (int i = 0; i < boardManager.getBoardSize() / 2; i++) {
                add(new JLabel(""));
            }
            add(restartButton);
            for (int i = 0; i < boardManager.getBoardSize() / 2; i++) {
                add(new JLabel(""));
            }
        } else {
            for (int i = 0; i < boardManager.getBoardSize() / 2; i++) {
                add(new JLabel(""));
            }
            add(restartButton);
            for (int i = 0; i < boardManager.getBoardSize() / 2 - 1; i++) {
                add(new JLabel(""));
            }
        }
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    private void onClick(JButton buttonClicked) {
        gameManager.addClick();

        var firstButtonPosition = boardManager.getBoardPosition(buttonClicked.getText());

        changeButtonsPositions(firstButtonPosition.getXPosition(), firstButtonPosition.getYPosition());

        boolean isOrdered = boardManager.isOrdered();
        if (isOrdered) {
            gameFinished();
        }
    }

    public static void main(String[] args) {
        new JogoDosOito();
    }

    private void gameFinished() {
        //Tela de vitória
        System.out.println("Game finished");
        System.out.println("You finish with: " + gameManager.getClickQuantity() + " tries");
    }

    private void changeButtonsPositions(int buttonXPosition, int buttonYPosition) {
        boolean hasEmptySpaceInSurround = boardManager.hasEmptySpaceInSurround(buttonXPosition, buttonYPosition);

        if (hasEmptySpaceInSurround) {
            int value = boardManager.getBoardValue(buttonXPosition, buttonYPosition);
            BoardPosition emptyPosition = boardManager.getEmptyPositions();

            boardManager.setBoardPositions(value, emptyPosition);
            boardManager.setBoardPositions(0, new BoardPosition(buttonXPosition, buttonYPosition));

            updateBoard();
        }
    }

    private void updateBoard() {
        for (int i = 0; i < boardManager.getBoardSize(); i++) {
            for (int j = 0; j < boardManager.getBoardSize(); j++) {
                JButton button = buttons[i][j];
                int value = boardManager.getBoardValue(i, j);
                button.setText(value == 0 ? "" : String.valueOf(value));
            }
        }
    }

    private void restartGame() {
        System.out.println("restart");
        boardManager.resetBoard();
        updateBoard();
    }
}
