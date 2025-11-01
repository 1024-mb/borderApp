package com.fuelprices.fuelprices;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.regex.Pattern;

// TODO: make the windows all the same size
// TODO: make the windows stop not responding on clicking buttons
// TODO: Add stylesheets & details about the car - modify the query to google maps

public class settingsController {
    @FXML
    private Label titlePane;
    @FXML
    private TextField postCodeField;
    @FXML
    private Label errorLbl1;
    @FXML
    private Label errorLbl2;
    @FXML
    private Label errorLbl3;
    @FXML
    private TextField fuelTank;
    @FXML
    private TextField fuelConsumption;


    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch(NumberFormatException e){
            return false;
        }
    }

    public void updateDetails() {
        String Postcode = postCodeField.getText();
        String regex = "-?\\d+(\\.\\d+)?";

        if(Postcode != null) {
            if(!Postcode.isEmpty() && Pattern.matches(regex, Postcode) && Postcode.length() == 6) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\FUJITSU\\JavaPrograms\\fuelPrices\\src\\main\\resources\\com\\fuelprices\\fuelprices\\registeredPostCode.txt"))) {
                    bufferedWriter.write(Postcode);
                    errorLbl1.setStyle("-fx-color: green;");
                    errorLbl1.setText("Postcode Updated!");

                } catch (IOException e) {
                    errorLbl1.setText("Please Enter A Valid Postcode");
                }
            }
        }

        String tankSize = fuelTank.getText();
        if(tankSize != null) {
            if(!tankSize.isEmpty() && isNumeric(tankSize)) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\FUJITSU\\JavaPrograms\\fuelPrices\\src\\main\\resources\\com\\fuelprices\\fuelprices\\fuelTank.txt"))) {
                    bufferedWriter.write(tankSize);
                    errorLbl2.setStyle("-fx-color: green;");
                    errorLbl2.setText("Tank Size Updated!");

                } catch (IOException e) {
                    errorLbl2.setText("Please Enter A Valid Number");
                }
            }
        }



        String consumption = fuelConsumption.getText();
        if(consumption != null) {
            if(!tankSize.isEmpty() && isNumeric(consumption)) {
                try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter("C:\\Users\\FUJITSU\\JavaPrograms\\fuelPrices\\src\\main\\resources\\com\\fuelprices\\fuelprices\\fuelConsumption.txt"))) {
                    bufferedWriter.write(consumption);
                    errorLbl3.setStyle("-fx-color: green;");
                    errorLbl3.setText("Fuel Consumption Updated!");

                } catch (IOException e) {
                    errorLbl3.setText("Please Enter A Valid Number");
                }
            }
        }

    }

    public void backToHome(ActionEvent event) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage.fxml"));
        Parent root = loader.load();

        // Get the controller linked to the FXML
        homePageController HomePageController = loader.getController();


        // Get stage from event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        HomePageController.start(stage);
        stage.setScene(new Scene(root));
        stage.show();

    }


}
