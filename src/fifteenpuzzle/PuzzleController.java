/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fifteenpuzzle;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;

public class PuzzleController {

    private static final int SIZE = 500;
    private static final int NB_SQUARE = 4;
    private static final int SQUARE_SIZE = SIZE / NB_SQUARE;
    private static final Color BACKGROUND_COLOR = Color.WHITE;

    private int emptyPosition;
    private GraphicsContext gc;
    private int lastSquare = NB_SQUARE * NB_SQUARE - 1;
    private boolean isSquareClickable;
    private List<Integer> ls = new ArrayList();

    public PuzzleController(Canvas canvas) {
        this.gc = canvas.getGraphicsContext2D();
        isSquareClickable = true;
        generateRandomDistribution();
    }

    private void generateRandomDistribution() {
        int size = NB_SQUARE * NB_SQUARE;
        for (int i = 0; i < size - 1; i++) {
            ls.add(i + 1);
        }
        Collections.shuffle(ls);

        ls.add(size - 1, 0);
        emptyPosition = ls.size() - 1;
    }

    public boolean testSuccessfulEnd() {
        if (ls.get(ls.size() - 1) > 0) {
            return false;
        }

        for (int i = 0; i < ls.size() - 1; i++) {
            if (ls.get(i) != i + 1) {
                return false;
            }
        }

        return true;
    }

    public void move(int x, int y) {
        if (!isSquareClickable) {
            return;
        }

        int squareClickedCol = x / SQUARE_SIZE;
        int squareClickedRow = y / SQUARE_SIZE;

        int emptySquareCol = emptyPosition % NB_SQUARE;
        int emptySquareRow = emptyPosition / NB_SQUARE;

        int direction = 0;
        if (squareClickedCol == emptySquareCol) {
            if (squareClickedRow - emptySquareRow == 1) {
                direction = 4;
            } else if (squareClickedRow - emptySquareRow == -1) {
                direction = -4;
            }

        } else if (squareClickedRow == emptySquareRow) {
            if (squareClickedCol - emptySquareCol == 1) {
                direction = 1;
            } else if (squareClickedCol - emptySquareCol == -1) {
                direction = -1;
            }
        }

        if (direction != 0) {
            int newEmptyPosition = emptyPosition + direction;
            ls.set(emptyPosition, ls.get(newEmptyPosition));
            emptyPosition = newEmptyPosition;
            ls.set(emptyPosition, 0);
        }

        draw();
    }

    public void draw() {
        gc.setFill(BACKGROUND_COLOR);
        gc.fillRect(0, 0, SIZE * SIZE, SIZE * SIZE);
        int index = 0;
        Color squareColor = Color.LIGHTBLUE;
        Color textColor = Color.BLACK;
        for (int i = 0; i < NB_SQUARE; i++) {
            for (int j = 0; j < NB_SQUARE; j++) {
                index = i + NB_SQUARE * j;

                if (testSuccessfulEnd()) {
                    squareColor = Color.LIGHTGREEN;
                    textColor = Color.WHITE;
                    gc.setFill(textColor);
                    gc.setFont(Font.font("Serif", 42));
                    gc.strokeText("Congratulations", SIZE / 4, SIZE / 2);
                    isSquareClickable = false;
                }

                if (ls.get(index) != 0) {
                    int x = i * SQUARE_SIZE;
                    int y = j * SQUARE_SIZE;

                    gc.setFill(squareColor);
                    gc.fillRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

                    gc.setFill(textColor);
                    gc.strokeRect(x, y, SQUARE_SIZE, SQUARE_SIZE);

                    gc.setFill(textColor);
                    gc.setFont(Font.font("Serif", 42));
                    gc.strokeText(ls.get(index) + "", x + SQUARE_SIZE / 3, y + SQUARE_SIZE / 1.5);
                }
            }
        }
    }
}
