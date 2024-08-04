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
    private final Canvas canvas = new Canvas(Main.WORLD_SIZE * Main.RECTANGLE_SIZE, Main.WORLD_SIZE * Main.RECTANGLE_SIZE);
    private final GraphicsContext gc = canvas.getGraphicsContext2D();
    private final WritableImage buffer = new WritableImage(Main.WORLD_SIZE, Main.WORLD_SIZE);
    private final PixelWriter pixelWriter = buffer.getPixelWriter();
    private final World world = new World(Main.WORLD_SIZE, this::drawAlive, this::drawDead);
    private final Benchmark benchmark = Benchmark.getInstance();
    private final Label benchmarkLabel = new Label();

    public Main() {
        this.gc.setImageSmoothing(false);
        // draw full black screen
        this.gc.setFill(Color.BLACK);
        this.gc.fillRect(0, 0, Main.DISPLAY_SIZE, Main.DISPLAY_SIZE);
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
                this.world.tick();
                Platform.runLater(this::updateGUI);
            }
        });
        thread.setDaemon(true);
        thread.start();
    }

    private void drawAlive(int x, int y) {
        this.pixelWriter.setColor(x, y, Color.DARKGREEN);
    }

    private void drawDead(int x, int y) {
        this.pixelWriter.setColor(x, y, Color.BLACK);
    }

    private void updateGUI() {
        this.gc.drawImage(this.buffer, 0, 0, Main.DISPLAY_SIZE, Main.DISPLAY_SIZE);
        this.benchmark.stop();
        this.benchmarkLabel.setText(this.benchmark.getText());
    }
}
