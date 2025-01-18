package pl.pwr.ite.dynak.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.control.*;
import javafx.util.Duration;
import pl.pwr.ite.dynak.main.Diner;
import pl.pwr.ite.dynak.threads.Client;

public class AppGUI extends Application {
    private Diner diner;
    VBox mainVBox = new VBox();
    HBox mainHBox = new HBox();
    GridPane mainGridPane = new GridPane();
    GridPane primaryQueueGP = new GridPane();
    Slider timeSlider = new Slider();
    Slider randomTimeSlider = new Slider();
    Button startButton = new Button("Start");
    TextField maxClientsField = new TextField();
    Scene scene = new Scene(mainVBox, 1000, 800);
    public void setupLayout() {
        mainVBox.getChildren().addAll(mainGridPane, mainHBox);
        timeSlider.setMax(5000);
        timeSlider.setShowTickLabels(true);
        timeSlider.setShowTickMarks(true);
        timeSlider.setMajorTickUnit(1000);
        timeSlider.setMinorTickCount(500);
        timeSlider.setPrefHeight(30);
        timeSlider.setPrefWidth(400);
        randomTimeSlider.setMax(5000);
        randomTimeSlider.setShowTickLabels(true);
        randomTimeSlider.setShowTickMarks(true);
        randomTimeSlider.setMajorTickUnit(1000);
        randomTimeSlider.setMinorTickCount(500);
        randomTimeSlider.setPrefHeight(30);
        randomTimeSlider.setPrefWidth(400);
        maxClientsField.setPromptText("Client amount");
        maxClientsField.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            if (!event.getCharacter().matches("\\d")) {
                event.consume();
            }
        });
        mainGridPane.setHgap(10);
        mainGridPane.setVgap(10);
        mainGridPane.add(timeSlider, 0, 1);
        mainGridPane.add(new Label("Tick duration:"), 0, 0);
        mainGridPane.add(randomTimeSlider, 0, 3);
        mainGridPane.add(maxClientsField, 1, 1);
        mainGridPane.add(new Label("Max random time amount:"), 0, 2);
        mainGridPane.add(startButton, 1, 3);
        startButton.setOnAction(e -> {
            this.diner = new Diner(Integer.parseInt(maxClientsField.getText()), (int)timeSlider.getValue(),(int)randomTimeSlider.getValue());
            try {
                diner.startSimulation();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });
        mainHBox.getChildren().add(primaryQueueGP);
    }
    private void updateQueues() {
        Platform.runLater(() -> {
            for (Spot spot : spots) {

            }
        });
    }
    @Override
    public void start(Stage stage) {
        setupLayout();
        /*
        Spot spot = new Spot(20,20, Color.LIGHTSEAGREEN, "B");
        Spot spotTwo = new Spot(20,20, Color.LIGHTGREEN, "C");
        Queue queue = new Queue(400, 20, Color.GREY);
        primaryQueueGP.add(queue, 0, 0, 20, 1);
        primaryQueueGP.add(spot, 0, 0);
        primaryQueueGP.add(spotTwo, 1, 0);
         */
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
