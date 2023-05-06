package src.Managers;

import java.util.Arrays;
import java.util.HashMap;

public class BoardManager {
    public BoardManager() {
        resetBoard();
    }

    private int[][] board = {{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
    private final HashMap<String, BoardPosition> boardPositions = new HashMap<>();
    private BoardPosition emptyPosition = new BoardPosition(2, 2);

    public BoardPosition getBoardPosition(String boardValue) {
        return boardPositions.get(boardValue);
    }

    public String getBoard() {
        return Arrays.deepToString(board);
    }

    public int getBoardValue(int xPosition, int yPosition) {
        return board[xPosition][yPosition];
    }

    public BoardPosition getEmptyPositions() {
        return emptyPosition;
    }

    public void setBoardPositions(Integer boardValue, BoardPosition boardPosition) {
        board[boardPosition.getXPosition()][boardPosition.getYPosition()] = boardValue;
        boardPositions.put(boardValue.toString(), boardPosition);
    }

    public void setEmptyPosition(BoardPosition boardPosition) {
        emptyPosition = boardPosition;
    }

    public void resetBoard() {
        iniatializeBoardPositions();
        board = new int[][]{{1, 2, 3}, {4, 5, 6}, {7, 8, 0}};
        emptyPosition = new BoardPosition(2, 2);
    }

    private void iniatializeBoardPositions() {
        int boardValue = 1;
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardPositions.put(Integer.toString(boardValue), new BoardPosition(i, j));
                boardValue++;

                if (i == 2 && j == 2) {
                    boardPositions.put("0", new BoardPosition(i, j));
                }
            }
        }
    }

    private int safeSearchInBoard(int x, int y) {
        try {
            return this.getBoardValue(x, y);
        } catch (Exception e) {
            return -1;
        }
    }

    public boolean hasEmptySpaceInSurround(int xPosition, int yPosition) {
        boolean hasEmptyAbove = safeSearchInBoard(xPosition, yPosition + 1) == 0;
        boolean hasEmptyBelow = safeSearchInBoard(xPosition, yPosition - 1) == 0;
        boolean hasEmptyLeft = safeSearchInBoard(xPosition - 1, yPosition) == 0;
        boolean hasEmptyRight = safeSearchInBoard(xPosition + 1, yPosition) == 0;

//		System.out.println(xPosition);
//		System.out.println(yPosition);
//		System.out.println(safeSearchInBoard(xPosition, yPosition + 1));
//		System.out.println(safeSearchInBoard(xPosition, yPosition - 1));
//		System.out.println(safeSearchInBoard(xPosition - 1, yPosition));
//		System.out.println(safeSearchInBoard(xPosition + 1, yPosition));

        return hasEmptyBelow || hasEmptyLeft || hasEmptyAbove || hasEmptyRight;
    }
}
