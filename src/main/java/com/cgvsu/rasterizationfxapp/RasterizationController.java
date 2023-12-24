package com.cgvsu.rasterizationfxapp;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;

import com.cgvsu.rasterization.*;
import javafx.scene.paint.Color;

public class RasterizationController {
    private Triangle triangle = new Triangle(400, 100, 100, 400, 600, 500);

    private Vector2f vertToMove = triangle.v1();
    @FXML
    AnchorPane anchorPane;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        anchorPane.prefWidthProperty().addListener((ov, oldValue, newValue) -> canvas.setWidth(newValue.doubleValue()));
        anchorPane.prefHeightProperty().addListener((ov, oldValue, newValue) -> canvas.setHeight(newValue.doubleValue()));

        triangle.drawTriangle(canvas.getGraphicsContext2D());
    }

    public void handleMouse(MouseEvent e) {
        if (e.isPrimaryButtonDown()) {
            vertToMove.set((float) e.getSceneX(), (float) e.getSceneY());
            redrawTriangle();
        }
    }

    public void handleKey(KeyEvent e) {
        if (e.getEventType() == KeyEvent.KEY_PRESSED) {
            switch (e.getCode()) {
                case Q -> {
                    triangle.setDefaultColors();
                    redrawTriangle();
                }
                case W -> {
                    triangle.randomizeColors();
                    redrawTriangle();
                }
            }
            switch (e.getText()) {
                case "1" -> vertToMove = triangle.v1();
                case "2" -> vertToMove = triangle.v2();
                case "3" -> vertToMove = triangle.v3();
            }
        }
    }
    private void redrawTriangle() {
        canvas.getGraphicsContext2D().clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        triangle.drawTriangle(canvas.getGraphicsContext2D());
    }

}