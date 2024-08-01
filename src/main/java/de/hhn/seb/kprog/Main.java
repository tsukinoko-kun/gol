package de.hhn.seb.kprog;


import javafx.application.Application;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.PixelWriter;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {
    // static values
    public final static int WORLD_SIZE = 512;
    private final static int RECTANGLE_SIZE = 1;
    private final static int DISPLAY_SIZE = WORLD_SIZE * RECTANGLE_SIZE;

    // components
    private final World world = new World(Main.WORLD_SIZE);
    private final Rules rules = new Rules(this.world);
    private final Canvas canvas = new Canvas(Main.WORLD_SIZE*Main.RECTANGLE_SIZE, Main.WORLD_SIZE*Main.RECTANGLE_SIZE);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final WritableImage buffer = new WritableImage(Main.WORLD_SIZE, Main.WORLD_SIZE);
    private final PixelWriter pixelWriter = buffer.getPixelWriter();
    private final Benchmark benchmark = Benchmark.getInstance();
    private final Label benchmarkLabel = new Label();

    public Main() {
        this.gc.setImageSmoothing(false);
    }

    public static void main(String[] args) {
        Application.launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Game of Life");
        VBox root = new VBox();
        root.getChildren().add(benchmarkLabel);
        root.getChildren().add(this.canvas);
        int sceneSize = Main.WORLD_SIZE * Main.RECTANGLE_SIZE;
        Scene scene = new Scene(root, sceneSize, sceneSize + 50);


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
                this.pixelWriter.setColor(x, y, this.world.isAlive(x, y) ? Color.DARKGREEN : Color.BLACK);
            }
        }
        this.gc.drawImage(this.buffer, 0, 0, Main.DISPLAY_SIZE, Main.DISPLAY_SIZE);
        this.benchmark.stop();
    }
}
