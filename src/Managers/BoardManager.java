package src.Managers;

import java.util.*;
import java.util.stream.IntStream;

public class BoardManager {
    public BoardManager() {
        resetBoard();
    }

    private int boardSize = 3;
    private final int[][] board = new int[boardSize][boardSize];
    private final HashMap<String, BoardPosition> boardPositions = new HashMap<>();

    public BoardPosition getBoardPosition(String boardValue) {
        return boardPositions.get(boardValue);
    }

    public String getBoard() {
        return Arrays.deepToString(board);
    }

    public int getBoardSize() {
        return boardSize;
    }

    public int getBoardValue(int xPosition, int yPosition) {
        return board[xPosition][yPosition];
    }

    public BoardPosition getEmptyPositions() {
        return boardPositions.get("0");
    }

    public void setBoardPositions(Integer boardValue, BoardPosition boardPosition) {
        board[boardPosition.getXPosition()][boardPosition.getYPosition()] = boardValue;
        boardPositions.put(boardValue.toString(), boardPosition);
    }

    public void setBoardSize(int boardSize) {
        this.boardSize = boardSize;
    }

    public void resetBoard() {
        ArrayList<Integer> defaultValuesList = new ArrayList<>();
        IntStream.range(0, 9).forEach(defaultValuesList::add);

        iniatializeBoardPositions(defaultValuesList);
    }

    private void iniatializeBoardPositions(ArrayList<Integer> defaultValues) {
        Random rand = new Random();
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int randomChoice = rand.nextInt(defaultValues.size());
                int boardValue = defaultValues.remove(randomChoice);

                board[i][j] = boardValue;
                boardPositions.put(Integer.toString(boardValue), new BoardPosition(i, j));
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

        return hasEmptyBelow || hasEmptyLeft || hasEmptyAbove || hasEmptyRight;
    }

    public boolean isOrdered() {
        int highestValue = 0;
        for (int i = 0; i < boardSize; i++) {
            for (int j = 0; j < boardSize; j++) {
                int currentValue = board[i][j];

                if (currentValue != 0 && currentValue > highestValue) {
                    highestValue = currentValue;
                } else if (currentValue == 0 && (i != boardSize - 1 || j != boardSize - 1)) {
                    return false;
                }
            }
        }

        return true;
    }
}
