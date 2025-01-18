package pl.pwr.ite.dynak.gui;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
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
import java.util.concurrent.BlockingQueue;
//launch command:
//C:\Users\Phario>java --module-path "C:\javafx-sdk-21.0.5\lib" --add-modules javafx.controls,javafx.fxml -jar C:\Users\Phario\IdeaProjects\lab05\target\lab05-1.0-SNAPSHOT.jar
public class AppGUI extends Application {
    private Diner diner;
    VBox mainVBox = new VBox();
    VBox secondaryVBox = new VBox();
    VBox staffVBox = new VBox();
    HBox mainHBox = new HBox();
    HBox secondaryHBox = new HBox();
    GridPane mainGridPane = new GridPane();
    Slider timeSlider = new Slider();
    Slider randomTimeSlider = new Slider();
    Button startButton = new Button("Start");
    TextField maxClientsField = new TextField();
    Scene scene = new Scene(mainVBox, 1300, 800);
    GridPane primaryQueueGP = new GridPane();
    Queue primaryQueue = new Queue();
    GridPane foodQueue0GP = new GridPane();
    Queue foodQueue0 = new Queue();
    GridPane foodQueue1GP = new GridPane();
    Queue foodQueue1 = new Queue();
    GridPane checkoutQueue0GP = new GridPane();
    Queue checkoutQueue0 = new Queue();
    GridPane checkoutQueue1GP = new GridPane();
    Queue checkoutQueue1 = new Queue();
    GridPane checkoutQueue2GP = new GridPane();
    Queue checkoutQueue2 = new Queue();
    GridPane checkoutQueue3GP = new GridPane();
    Queue checkoutQueue3 = new Queue();
    GridPane tableZeroGP = new GridPane();
    GridPane tableOneGP = new GridPane();
    VBox tablesVB = new VBox();
    Queue tableZeroBG = new Queue();
    Queue tableOneBG = new Queue();
    Queue tableBG = new Queue(600, 30, Color.ROSYBROWN);
    Spot mainQueueSpot = new Spot(30,30,Color.RED,"m");
    Spot cook0 = new Spot(30,30, Color.LIGHTBLUE, "c0");
    Spot cook1 = new Spot(30,30, Color.LIGHTBLUE, "c1");
    Spot clerk0 = new Spot(30,30, Color.LIGHTBLUE, "s0");
    Spot clerk1 = new Spot(30,30, Color.LIGHTBLUE, "s1");
    Spot clerk2 = new Spot(30,30, Color.LIGHTBLUE, "s2");
    Spot clerk3 = new Spot(30,30, Color.LIGHTBLUE, "s3");
    private void updateQueue(BlockingQueue<Client> queue, GridPane queueGP, Queue queueBG) {
        Platform.runLater(() -> {
            int i = 0;
            queueGP.getChildren().clear();
            queueGP.add(queueBG, 0,0,30,1);
            for (Client client : queue) {
                queueGP.add(new Spot(client.getName()), i,0);
                i++;
            }
        });
    }
    private void updateTable(Client[] table, GridPane tableZeroGP, GridPane tableOneGP, Queue queueZeroBG, Queue queueOneBG) {
        Platform.runLater(() -> {
            tableZeroGP.getChildren().clear();
            tableOneGP.getChildren().clear();
            tableZeroGP.add(queueZeroBG, 0,0,30,1);
            tableOneGP.add(queueOneBG, 0,0,30,1);
            for (int i = 0; i < table.length; i++) {
                if (table[i] != null) {
                    if (i<5) {
                        tableZeroGP.add(new Spot(table[i].getName()), i, 0);
                    } else tableOneGP.add(new Spot(table[i].getName()), i, 0);
                }
            }
        });
    }
    public void setupLayout() {
        //setup
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
        //Queue placement
        primaryQueueGP.add(primaryQueue, 0, 0, 20, 1);
        foodQueue0GP.add(foodQueue0, 0, 0, 20, 1);
        foodQueue1GP.add(foodQueue1, 0, 0, 20, 1);
        checkoutQueue0GP.add(checkoutQueue0, 0, 0, 20, 1);
        checkoutQueue1GP.add(checkoutQueue1, 0, 0, 20, 1);
        checkoutQueue2GP.add(checkoutQueue2, 0, 0, 20, 1);
        checkoutQueue3GP.add(checkoutQueue3, 0, 0, 20, 1);
        tableZeroGP.add(tableZeroBG, 0, 0, 20, 1);
        tableOneGP.add(tableOneBG, 0, 0, 20, 1);
        tablesVB.getChildren().addAll(tableZeroGP, tableBG, tableOneGP);
        staffVBox.getChildren().addAll(mainQueueSpot, cook0, cook1, clerk0, clerk1, clerk2, clerk3);
        secondaryVBox.getChildren().addAll(primaryQueueGP, foodQueue0GP,foodQueue1GP, checkoutQueue0GP, checkoutQueue1GP, checkoutQueue2GP, checkoutQueue3GP);
        secondaryHBox.getChildren().addAll(staffVBox, secondaryVBox, tablesVB);
        mainHBox.getChildren().add(secondaryHBox);
        //Button setup
        startButton.setOnAction(e -> {
            this.diner = new Diner(Integer.parseInt(maxClientsField.getText()), (int)timeSlider.getValue(),(int)randomTimeSlider.getValue());
            try {
                diner.startSimulation();
                Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), event -> {
                    updateQueue(diner.getPrimaryQueue(), primaryQueueGP, primaryQueue);
                    updateQueue(diner.getFoodQueue0(), foodQueue0GP, foodQueue0);
                    updateQueue(diner.getFoodQueue1(), foodQueue1GP, foodQueue1);
                    updateQueue(diner.getCheckoutQueue0(), checkoutQueue0GP, checkoutQueue0);
                    updateQueue(diner.getCheckoutQueue1(), checkoutQueue1GP, checkoutQueue1);
                    updateQueue(diner.getCheckoutQueue2(), checkoutQueue2GP, checkoutQueue2);
                    updateQueue(diner.getCheckoutQueue3(), checkoutQueue3GP, checkoutQueue3);
                    updateTable(diner.getTable(), tableZeroGP, tableOneGP, tableZeroBG, tableOneBG);
                }
                ));
                timeline.setCycleCount(Animation.INDEFINITE);
                timeline.play();
            } catch (InterruptedException ex) {
                Thread.currentThread().interrupt();
            }
        });
    }

    @Override
    public void start(Stage stage) {
        setupLayout();
        stage.setScene(scene);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
