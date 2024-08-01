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

public class Main extends Application {
    // static values
    public final static int WORLD_SIZE = 512;
    private final static int RECTANGLE_SIZE = 1;

    // components
    private final World world = new World(Main.WORLD_SIZE);
    private final Rules rules = new Rules(this.world);
    private final Rectangle[] rectangles = new Rectangle[Main.WORLD_SIZE * Main.WORLD_SIZE];
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
            for (int x = 0; x < Main.WORLD_SIZE; x++) {
                int i = y * Main.WORLD_SIZE + x;
                Rectangle rectangle = new Rectangle(Main.RECTANGLE_SIZE, Main.RECTANGLE_SIZE);
                rectangle.setFill(Color.BLACK);
                grid.add(rectangle, x, y);
                this.rectangles[i] = rectangle;
            }
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
                int i = y * Main.WORLD_SIZE + x;
                this.rectangles[i].setFill(this.world.isAlive(x, y) ? Color.DARKGREEN : Color.BLACK);
            }
        }
        this.benchmark.stop();
    }
}
