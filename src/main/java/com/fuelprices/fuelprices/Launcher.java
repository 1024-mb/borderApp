package com.fuelprices.fuelprices;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("prism.order", "sw"); // safest software rendering
        System.setProperty("prism.vsync", "false");
        Application.launch(getStarted.class, args);
    }
}
