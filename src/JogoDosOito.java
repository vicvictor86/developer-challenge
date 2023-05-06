package src;

import src.Managers.BoardPosition;

import java.awt.Font;
import java.awt.GridLayout;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Arrays;
import java.util.HashMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

public class JogoDosOito extends JFrame implements KeyListener {
	private HashMap<String, BoardPosition> boardPositions = new HashMap<>();
	private int[][] board = { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
	private BoardPosition emptyPosition = new BoardPosition(2, 2);
	private JButton[][] buttons = new JButton[3][3];
	private JButton restartButton;

	public JogoDosOito() {
		super("Jogo dos Oito");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setSize(300, 300);
		setLayout(new GridLayout(4, 3));

		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton button = new JButton();
				button.setFont(new Font("Arial", Font.BOLD, 36));
				button.addActionListener(e -> {
					buttonClicked(button);
				});

				buttons[i][j] = button;
				add(button);
			}
		}
		iniatializeBoardPositions();

		restartButton = new JButton("Reiniciar");
		restartButton.addActionListener(e -> {
			restartGame();
		});
		add(new JLabel(""));
		add(restartButton);
		add(new JLabel(""));

		addKeyListener(this);
		setFocusable(true);
		updateBoard();
		setVisible(true);
	}

	private void iniatializeBoardPositions() {
		Integer boardValue = 1;
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				boardPositions.put(boardValue.toString(), new BoardPosition(i, j));
				boardValue++;

				if(i == 2 && j == 2) {
					boardPositions.put("0", new BoardPosition(i, j));
				}
			}
		}
	}

	public void keyPressed(KeyEvent e) {
		int keyCode = e.getKeyCode();
		switch (keyCode) {
		case KeyEvent.VK_UP:
			mover(1, 0);
			break;
		case KeyEvent.VK_DOWN:
			mover(-1, 0);
			break;
		case KeyEvent.VK_LEFT:
			mover(0, 1);
			break;
		case KeyEvent.VK_RIGHT:
			mover(0, -1);
			break;
		}
	}

	public void keyTyped(KeyEvent e) {
	}

	public void keyReleased(KeyEvent e) {
	}

	private void buttonClicked(JButton buttonClicked) {
		var firstButtonPosition = boardPositions.get(buttonClicked.getText());

		changePositions(firstButtonPosition.getXPosition(), firstButtonPosition.getYPosition());
		System.out.println(Arrays.deepToString(board));
	}

	private void mover(int linha, int coluna) {
		int linhaVazia = -1;
		int colunaVazia = -1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] == 0) {
					linhaVazia = i;
					colunaVazia = j;
				}
			}
		}
		int novaLinha = linhaVazia + linha;
		int novaColuna = colunaVazia + coluna;
		if (novaLinha < 0 || novaLinha > 2 || novaColuna < 0 || novaColuna > 2) {
			// movimento inválido
			return;
		}
		board[linhaVazia][colunaVazia] = board[novaLinha][novaColuna];
		board[novaLinha][novaColuna] = 0;
		updateBoard();
		if (jogoConcluido()) {
			JOptionPane.showMessageDialog(this, "Parabéns, você venceu!");
			restartGame();
		}
	}

	public static void main(String[] args) {
		new JogoDosOito();
	}

	private boolean jogoConcluido() {
		int count = 1;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j] != count % 9) {
					return false;
				}
				count++;
			}
		}
		return true;
	}

	private int safeSearchInBoard(int x, int y) {
		try {
			return board[x][y];
		} catch (Exception e) {
			return -1;
		}
	}

	private boolean hasEmptySpaceInSurround(int xPosition, int yPosition) {
		boolean hasEmptyAbove = safeSearchInBoard(xPosition, yPosition + 1) == 0;
		boolean hasEmptyBelow = safeSearchInBoard(xPosition, yPosition - 1) == 0;
		boolean hasEmptyLeft = safeSearchInBoard(xPosition - 1, yPosition) == 0;
		boolean hasEmptyRight = safeSearchInBoard(xPosition + 1, yPosition) == 0;

		System.out.println(xPosition);
		System.out.println(yPosition);
		System.out.println(safeSearchInBoard(xPosition, yPosition + 1));
		System.out.println(safeSearchInBoard(xPosition, yPosition - 1));
		System.out.println(safeSearchInBoard(xPosition - 1, yPosition));
		System.out.println(safeSearchInBoard(xPosition + 1, yPosition));

		return hasEmptyBelow || hasEmptyLeft || hasEmptyAbove || hasEmptyRight;
	}

	private void updateBoardPositions(Integer value, int xPosition, int yPosition) {
		board[xPosition][yPosition] = value;
		boardPositions.put(value.toString(), new BoardPosition(xPosition, yPosition));
	}

	private boolean changePositions(int buttonXPosition, int buttonYPosition) {
		boolean hasEmptySpaceInSurround = hasEmptySpaceInSurround(buttonXPosition, buttonYPosition);

		System.out.println(hasEmptySpaceInSurround);
		if(hasEmptySpaceInSurround) {
			int value = board[buttonXPosition][buttonYPosition];

			updateBoardPositions(0, buttonXPosition, buttonYPosition);
			updateBoardPositions(value, emptyPosition.getXPosition(), emptyPosition.getYPosition());
			emptyPosition.setPositions(buttonXPosition, buttonYPosition);

			updateBoard();
			return true;
		}

		return false;
	}

	private void updateBoard() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				JButton botao = buttons[i][j];
				int valor = board[i][j];
				if (valor == 0) {
					botao.setText("");
				} else {
					botao.setText(String.valueOf(valor));
				}
			}
		}
	}

	private void restartGame() {
		System.out.println("restart");
		board = new int[][] { { 1, 2, 3 }, { 4, 5, 6 }, { 7, 8, 0 } };
		updateBoard();
	}
}
