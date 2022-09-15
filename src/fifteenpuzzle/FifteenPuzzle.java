/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fifteenpuzzle;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 *
 * @author Win10RS8
 */
public class FifteenPuzzle extends Application {
    
    @Override
    public void start(Stage primaryStage) {
       primaryStage.setTitle("Fifteen Puzzle");
        Group root = new Group();
        Canvas canvas = new Canvas(500, 500);
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, Color.LIGHTGRAY);
        primaryStage.setScene(scene);
        primaryStage.show();

        PuzzleController puzzleController = new PuzzleController(canvas);
        puzzleController.draw();

        scene.setOnMouseClicked(new EventHandler<MouseEvent>() {
            public void handle(MouseEvent e) {
                puzzleController.move((int) e.getX(), (int) e.getY());
            }
        });
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
