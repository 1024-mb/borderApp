package com.fuelprices.fuelprices;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.stage.Stage;

public final class FxStabilizer {
    public static void stabilize(Stage stage) {
        Platform.runLater(() -> {
            Scene scene = stage.getScene();
            if (scene != null && scene.getRoot() != null) {
                scene.getRoot().applyCss();
                scene.getRoot().requestLayout();
                Platform.runLater(() -> {
                    scene.getRoot().applyCss();
                    scene.getRoot().requestLayout();
                });
            }
        });
    }
}