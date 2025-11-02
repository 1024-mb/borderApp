package com.fuelprices.fuelprices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedWriter;

import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

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

    public void submitPostcode(ActionEvent event) throws Exception {
        String Postcode = postCodeField.getText();
        String regex = "-?\\d+(\\.\\d+)?";

        if(Postcode != null) {
            if(!Postcode.isEmpty() && Pattern.matches(regex, Postcode) && Postcode.length() == 6) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\FUJITSU\\JavaPrograms\\fuelPrices\\src\\main\\resources\\com\\fuelprices\\fuelprices\\registeredPostCode.txt"))) {
                    bufferedWriter.write(Postcode);
                } catch (IOException e) {
                    errorLbl.setText("Please Enter A Valid Postcode");
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
