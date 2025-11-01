module com.fuelprices.fuelprices {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.web;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires net.synedra.validatorfx;
    requires org.kordamp.ikonli.javafx;
    requires org.kordamp.bootstrapfx.core;
    requires eu.hansolo.tilesfx;
    requires com.almasb.fxgl.all;
    requires org.json;
    requires java.net.http;
    requires javafx.graphics;
    requires org.jsoup;
    requires java.desktop;
    requires GMapsFX;
    requires jcef;
    requires javafx.swing;
    requires jcefmaven;
    requires org.seleniumhq.selenium.api;
    requires org.seleniumhq.selenium.chrome_driver;
    requires htmlunit;
    requires javafx.base;

    opens com.fuelprices.fuelprices to javafx.fxml;
    exports com.fuelprices.fuelprices;
}