package src;

import src.Managers.BoardManager;
import src.Managers.BoardPosition;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

public class JogoDosOito extends JFrame implements KeyListener {
    private final BoardManager boardManager = new BoardManager();
    private final JButton[][] buttons = new JButton[boardManager.getBoardSize()][boardManager.getBoardSize()];

    public JogoDosOito() {
        super("Jogo dos Oito");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 300);
        setLayout(new GridLayout(4, 3));

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
        add(new JLabel(""));
        add(restartButton);
        add(new JLabel(""));

        addKeyListener(this);
        setFocusable(true);
        updateBoard();
        setVisible(true);
    }

    public void keyPressed(KeyEvent e) {
    }

    public void keyTyped(KeyEvent e) {
    }

    public void keyReleased(KeyEvent e) {
    }

    private void onClick(JButton buttonClicked) {
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
