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
        var scene = new Scene(gridPane, 600, 400);
        var timeSlider = new Slider();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.add(timeSlider, 1, 1, 2, 1);
        timeSlider.setMin(100);
        timeSlider.setMax(10000);
        timeSlider.setShowTickLabels(true);
        timeSlider.setShowTickMarks(true);
        timeSlider.setMajorTickUnit(1000);
        timeSlider.setMinorTickCount(100);
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
