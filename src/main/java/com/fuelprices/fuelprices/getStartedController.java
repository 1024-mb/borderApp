package com.fuelprices.fuelprices;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.scene.input.MouseEvent;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class getStartedController {
    @FXML
    private TextField postCodeField;
    @FXML
    private Label errorLbl;
    @FXML
    private Text topBanner;
    @FXML
    private Text UserMsg;
    @FXML
    private Button skipBtn;
    @FXML
    public void setUpdateText() {
        topBanner.setText("UPDATE POSTCODE");
        skipBtn.setText("Cancel");
        skipBtn.addEventHandler(MouseEvent.MOUSE_CLICKED,
                new EventHandler<MouseEvent>() {
                    @Override
                    public void handle(MouseEvent mouseEvent) {
                        getStarted newgetStarted = new getStarted();

                        Node sourceNode = (Node) mouseEvent.getSource();
                        Scene scene = sourceNode.getScene();
                        Stage stage = (Stage) scene.getWindow();

                        newgetStarted.start(stage);
                    }
                });

        UserMsg.setText("Enter New Postcode");
    }


    public void submitPostcode(ActionEvent event) throws Exception {
        String Postcode = postCodeField.getText();
        if(Postcode != null) {
            if(!Postcode.equals("")) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\FUJITSU\\JavaPrograms\\fuelPrices\\src\\main\\resources\\com\\fuelprices\\fuelprices\\registeredPostCode.txt"))) {
                    bufferedWriter.write(Postcode);
                } catch (IOException e) {
                    errorLbl.setText("Error While Saving Settings");
                }
            }
        }
         getStarted newgetStarted = new getStarted();

         Node sourceNode = (Node) event.getSource();
         Scene scene = sourceNode.getScene();
         Stage stage = (Stage) scene.getWindow();

        newgetStarted.start(stage);
    }

    public void skipThis(ActionEvent event) throws Exception {
        try(BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\FUJITSU\\JavaPrograms\\fuelPrices\\src\\main\\resources\\com\\fuelprices\\fuelprices\\registeredPostCode.txt"))) {
            bufferedWriter.write("N/A");

            getStarted newgetStarted = new getStarted();

            Node sourceNode = (Node) event.getSource();
            Scene scene = sourceNode.getScene();
            Stage stage = (Stage) scene.getWindow();

            newgetStarted.start(stage);
        }

        catch(IOException e) {
            errorLbl.setText("Error While Saving Settings");
        }

    }
}
