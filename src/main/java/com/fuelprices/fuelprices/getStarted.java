package com.fuelprices.fuelprices;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class getStarted extends Application {
    @Override
    public void start(Stage stage){
        try(BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\FUJITSU\\JavaPrograms\\fuelPrices\\src\\main\\resources\\com\\fuelprices\\fuelprices\\registeredPostCode.txt"))) {
            String line1 = bufferedReader.readLine();

            if (!(line1==null)) {
                if(!line1.equals("")){
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage2.fxml"));
                    Parent root = loader.load();

                    homePageController controller = loader.getController();
                    controller.start(stage);

                    Scene scene = new Scene(root);
                    stage.setScene(scene);
                    stage.show();
                }
                else {
                    loadStartPage(stage);
                }

            }
            else {
                loadStartPage(stage);
            }

        } catch(IOException e) {
            e.printStackTrace();

        } catch (InterruptedException e) {
            throw new RuntimeException(e);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }

    public void loadStartPage(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("getStarted.fxml"));
        Parent root = loader.load();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
