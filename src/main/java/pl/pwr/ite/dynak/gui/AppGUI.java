package pl.pwr.ite.dynak.gui;

import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;

public class AppGUI extends Application {
    @Override
    public void start(Stage stage) {
        var gridPane = new GridPane();
        var timeSlider = new Slider();
        var startButton = new Button("Start");
        gridPane.setStyle("-fx-background-color: rgb(13, 17, 33)");
        gridPane.setGridLinesVisible(true);
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(timeSlider, 0, 0);
        gridPane.add(startButton, 3, 0);
        timeSlider.setMax(10000);
        timeSlider.setShowTickLabels(true);
        timeSlider.setShowTickMarks(true);
        timeSlider.setMajorTickUnit(1000);
        timeSlider.setMinorTickCount(100);
        var scene = new Scene(gridPane, 600, 400);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
