package com.fuelprices.fuelprices;

import javafx.application.Application;

public class Launcher {
    public static void main(String[] args) {
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("prism.vsync", "false");
        System.setProperty("javafx.animation.fullspeed", "true");

        Application.launch(getStarted.class, args);
    }
}
