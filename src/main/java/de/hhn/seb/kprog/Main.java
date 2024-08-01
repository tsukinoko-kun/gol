package de.hhn.seb.kprog;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

import java.util.LinkedList;

public class Main extends Application {
    // static values
    public final static int WORLD_SIZE = 128;
    private final static int RECTANGLE_SIZE = 32;

    // components
    private final World world = new World(Main.WORLD_SIZE);
    private final Rules rules = new Rules(this.world);
    private final LinkedList<LinkedList<Rectangle>> rectangles = new LinkedList<>();
    private final Benchmark benchmark = Benchmark.getInstance();
    private final Label benchmarkLabel = new Label();

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game of Life");
        VBox root = new VBox();
        root.getChildren().add(benchmarkLabel);
        int sceneSize = Main.WORLD_SIZE * Main.RECTANGLE_SIZE;
        Scene scene = new Scene(root, sceneSize, sceneSize + 50);

        GridPane grid = new GridPane();
        root.getChildren().add(grid);

        for (int y = 0; y < Main.WORLD_SIZE; y++) {
            LinkedList<Rectangle> row = new LinkedList<>();
            for (int x = 0; x < Main.WORLD_SIZE; x++) {
                // create a rectangle
                Rectangle rect = new Rectangle(Main.RECTANGLE_SIZE, Main.RECTANGLE_SIZE);
                rect.setFill(Color.BLACK);
                // set the position of the rectangle
                rect.setX(y * Main.RECTANGLE_SIZE);
                rect.setY(x * Main.RECTANGLE_SIZE);
                // add the rectangle to the rectangles row
                row.add(rect);
            }
            // add the row of rectangles to the grid
            grid.addRow(y, row.toArray(new Rectangle[0]));
            // add the row to the rectangles list
            this.rectangles.add(row);
        }

        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();

        // run updateGUI repeatedly in another thread
        var thread = new Thread(() -> {
            while (true) {
                this.benchmark.start();
                this.rules.tick();
                Platform.runLater(this::updateGUI);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void updateGUI() {
        this.benchmarkLabel.setText(this.benchmark.getText());
        for (int y = 0; y < Main.WORLD_SIZE; y++) {
            for (int x = 0; x < Main.WORLD_SIZE; x++) {
                this.rectangles.get(y).get(x).setFill(world.isAlive(x, y) ? Color.DARKGREEN : Color.BLACK);
            }
        }
        this.benchmark.stop();
    }
}
