package src;

import src.managers.BoardManager;
import src.managers.BoardPosition;
import src.managers.GameManager;
import src.managers.ScreenManager;
import src.screens.Welcome;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JogoDosOito extends JFrame implements KeyListener {
    private final ScreenManager screenManager;
    private final GameManager gameManager;
    private final BoardManager boardManager;

    private final JButton[][] buttons;
    private final JLabel clickQuantityLabel = new JLabel("Tentativas: 0");
    private final JLabel timeLabel = new JLabel("Tempo: 0 segs");

    public JogoDosOito(ScreenManager screenManager) {
        super("Jogo dos Oito");
        this.screenManager = screenManager;
        this.gameManager = screenManager.getGameManager();

        this.boardManager = gameManager.boardManager;

        this.buttons = new JButton[boardManager.getBoardSize()][boardManager.getBoardSize()];

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(gameManager.getScreenWidth(), gameManager.getScreenHeight());
        setLayout(new GridLayout(boardManager.getBoardSize() + 2, boardManager.getBoardSize()));

        addLabels();

        gameManager.startCounting(this.timeLabel);

        addButtons();

        addKeyListener(this);
        setFocusable(true);

        updateBoard();

        setVisible(true);
    }

    private void addLabels() {
        if (boardManager.getBoardSize() % 2 != 0) {
            add(timeLabel);
            add(clickQuantityLabel);
            for (int i = 0; i < boardManager.getBoardSize() - 2; i++) {
                add(new JLabel(""));
            }
        } else {
            for (int i = 0; i < boardManager.getBoardSize() / 2 - 1; i++) {
                add(new JLabel(""));
            }
            add(timeLabel);
            add(clickQuantityLabel);
            for (int i = 0; i < boardManager.getBoardSize() / 2 - 1; i++) {
                add(new JLabel(""));
            }
        }
    }

    private void addButtons() {
        for (int i = 0; i < boardManager.getBoardSize(); i++) {
            for (int j = 0; j < boardManager.getBoardSize(); j++) {
                JButton button = new JButton();
                button.setFont(new Font("Arial", Font.BOLD, 36));
                button.addActionListener(e -> onClick(button));

                buttons[i][j] = button;
                add(button);
            }
        }

        JButton backButton = new JButton("Voltar");
        backButton.addActionListener(screenManager);

        JButton restartButton = new JButton("Reiniciar");
        restartButton.addActionListener(e -> restartGame());

        addBottomButtons(restartButton, backButton);
    }

    private void addBottomButtons(JButton restartButton, JButton backButton) {
        for (int i = 0; i < boardManager.getBoardSize() / 2 - 1; i++) {
            add(new JLabel(""));
        }
        add(backButton);
        add(restartButton);
        if (boardManager.getBoardSize() % 2 != 0) {
            for (int i = 0; i < boardManager.getBoardSize() / 2; i++) {
                add(new JLabel(""));
            }
        } else {
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

    @Override
    public void dispose() {
        super.dispose();
        gameManager.stopCounting();
    }

    private void onClick(JButton buttonClicked) {
        var firstButtonPosition = boardManager.getBoardPosition(buttonClicked.getText());

        boolean isValidMove = changeButtonsPositions(firstButtonPosition.getXPosition(), firstButtonPosition.getYPosition());

        if (isValidMove) {
            gameManager.addClick();
            clickQuantityLabel.setText("Tentativas: " + gameManager.getClickQuantity());
        }

        boolean isOrdered = boardManager.isOrdered();
        if (isOrdered) {
            gameFinished();
        }
    }

    private void gameFinished() {
        //Tela de vitória
        System.out.println("Game finished");
        System.out.println("You finish with: " + gameManager.getClickQuantity() + " tries");
    }

    private boolean changeButtonsPositions(int buttonXPosition, int buttonYPosition) {
        boolean hasEmptySpaceInSurround = boardManager.hasEmptySpaceInSurround(buttonXPosition, buttonYPosition);

        if (hasEmptySpaceInSurround) {
            int value = boardManager.getBoardValue(buttonXPosition, buttonYPosition);
            BoardPosition emptyPosition = boardManager.getEmptyPositions();

            boardManager.setBoardPositions(value, emptyPosition);
            boardManager.setBoardPositions(0, new BoardPosition(buttonXPosition, buttonYPosition));

            updateBoard();
        }

        return hasEmptySpaceInSurround;
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
