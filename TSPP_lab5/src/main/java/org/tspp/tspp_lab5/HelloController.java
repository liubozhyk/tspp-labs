package org.tspp.tspp_lab5;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import java.net.URL;
import java.util.*;

public class HelloController implements Initializable {

    @FXML
    private Canvas drawLineCanvas;

    @FXML
    private AnchorPane drawRectangleAnchorPane;

    @FXML
    private Button startTimer;

    @FXML
    private Button stopTimer;

    private double lastX, lastY;
    private final Rectangle rect = new Rectangle(150,100);
    private Timeline timeline;

    @Override public void initialize(URL url, ResourceBundle resourceBundle) {
        initLineDrawing();
    }

    @FXML public void initLineDrawing() {
        GraphicsContext line = drawLineCanvas.getGraphicsContext2D();
        line.setLineDashes(5);
        line.setLineWidth(2);

        drawLineCanvas.addEventHandler(MouseEvent.MOUSE_PRESSED, event -> {
            lastX = event.getX();
            lastY = event.getY();
        });

        drawLineCanvas.addEventHandler(MouseEvent.MOUSE_DRAGGED, event -> {
            double x = event.getX();
            double y = event.getY();
            line.strokeLine(lastX, lastY, x, y);
            lastX = x;
            lastY = y;
        });
    }

    @FXML public void clearDraw() {
        GraphicsContext gc = drawLineCanvas.getGraphicsContext2D();
        gc.setFill(Color.WHITE);
        gc.fillRect(0, 0, drawLineCanvas.getWidth(), drawLineCanvas.getHeight());
    }

    @FXML public void initRectangleDrawing(MouseEvent event) {
        rect.setX(event.getX() - rect.getWidth() / 2);
        rect.setY(event.getY() - rect.getHeight() / 2);
        rect.setStroke(Color.BLACK);
        rect.setStrokeWidth(3);
        rect.setFill(Color.TRANSPARENT);
        drawRectangleAnchorPane.getChildren().setAll(rect);
    }

    @FXML public void animation() {
        timeline = new Timeline(new KeyFrame(Duration.millis(500), event -> {
            rect.setStroke(randomColor());
        }));

        startTimer.setOnMouseClicked(mouseEvent -> {
            if (timeline.getStatus() != Animation.Status.RUNNING) {
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
            }
        });

        stopTimer.setOnMouseClicked(mouseEvent -> {
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setContentText("Підтвердити зупинення анімації?");
            alert.showAndWait();

            if (alert.getResult() == ButtonType.OK) {
                if (timeline.getStatus() == Animation.Status.RUNNING) {
                    timeline.stop();
                }
            }
        });
    }

    private Color randomColor() {
        return Color.rgb((int) (Math.random() * 255), (int) (Math.random() * 255), (int) (Math.random() * 255));
    }
}

