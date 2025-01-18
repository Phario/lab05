package pl.pwr.ite.dynak.gui;

import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Queue extends StackPane {
    private Rectangle rectangle;

    public Queue(double width, double height, Color color) {
        rectangle = new Rectangle(width, height);
        rectangle.setFill(color);
        rectangle.setStroke(Color.BLACK);
        rectangle.setStrokeWidth(2);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
        this.getChildren().addAll(rectangle);
    }

    public void setColor(Color color) {
        rectangle.setFill(color);
    }
}
