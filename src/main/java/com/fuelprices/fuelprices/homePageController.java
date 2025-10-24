package com.fuelprices.fuelprices;

import com.lynden.gmapsfx.GoogleMapView;
import com.lynden.gmapsfx.javascript.object.*;
import com.lynden.gmapsfx.service.directions.*;
import javafx.animation.Interpolator;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Worker;
import javafx.embed.swing.SwingNode;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import javafx.util.Duration;
import me.friwi.jcefmaven.CefAppBuilder;
import me.friwi.jcefmaven.CefInitializationException;
import me.friwi.jcefmaven.UnsupportedPlatformException;
import org.cef.CefApp;
import org.cef.CefClient;
import org.cef.browser.CefBrowser;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.HtmlPage;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.lynden.gmapsfx.GoogleMapView.*;
import com.lynden.gmapsfx.MapComponentInitializedListener;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import javax.swing.*;

import static com.almasb.fxgl.core.math.FXGLMath.floor;
import static java.lang.Math.round;
// import com.lynden.gmapsfx.javascript.object.MapType;


public class homePageController extends Application implements Initializable {
    private final String API_KEY_GMAPS = System.getenv("API_KEY_GMAPS");
    private final String API_KEY_TOMTOM = System.getenv("API_KEY_TOMTOM");
    private final String API_KEY_OPENMAP = System.getenv("API_KEY_OPENMAP");;
    @FXML
    private ImageView img1;

    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;
    boolean clicked = false;

    @FXML
    private TableView<PetrolPrices> petrolPriceTable;
    @FXML
    private VBox Updates;
    @FXML
    private TableColumn<PetrolPrices, String> Country;
    @FXML
    private TableColumn<PetrolPrices, String> Esso;
    @FXML
    private TableColumn<PetrolPrices, String> Shell;
    @FXML
    private TableColumn<PetrolPrices, String> SPC;
    @FXML
    private TableColumn<PetrolPrices, String> Caltex;


    @FXML
    private TableView<Routes> routeTable;
    @FXML
    private TableColumn<Routes, String> Location;
    @FXML
    private TableColumn<Routes, String> Distance;
    @FXML
    private TableColumn<Routes, String> TimeDestination;

    @FXML
    private TextField SGDIn;

    @FXML
    private TextField MYRIn;

    @FXML
    private Label TimeLbl;
    @FXML
    private Label DistanceLbl;
    @FXML
    private Label CostLbl;
    @FXML
    private WebView mapsWebView;
    @FXML
    private AnchorPane GMapsLink;
    @FXML
    private Label img1lbl;
    @FXML
    private Label img2lbl;
    @FXML
    private Label checkpointLbl;

    public void commonHandler(ImageView imageView) {
        if(clicked) {
            imageView.setFitHeight(259.2);
            imageView.setFitWidth(460.8);
        } else {
            imageView.setFitHeight(259.2*2);
            imageView.setFitWidth(460.8*2);
        }

        clicked = !clicked;
    }

    public String getJourneyData() throws IOException, InterruptedException {
        BufferedReader bufferedReader = new BufferedReader(new FileReader("C:\\Users\\FUJITSU\\JavaPrograms\\fuelPrices\\src\\main\\resources\\com\\fuelprices\\fuelprices\\registeredPostCode.txt"));
        String Postcode = bufferedReader.readLine();

        String regex = "-?\\d+(\\.\\d+)?";

        if(!(Postcode == null)) {
            if (!(!Postcode.isEmpty() && Pattern.matches(regex, Postcode) && Postcode.length() == 6)) {
                Postcode = "588185";
            }
        }
        else {
            Postcode = "588185";
        }

        return Postcode;
    }

    public void getRoute(String Postcode) {
        try {

            Postcode = Postcode + ", Singapore";
                    // road, mall, mall, legoland, denga bay

            String[] destinations = {
            "Jalan Wong Ah Fook, Bandar Johor Bahru, 80000 Johor Bahru, Johor Darul Ta'zim",
            "106-108, Jalan Wong Ah Fook, Bandar Johor Bahru, 80000 Johor Bahru, Johor",
            "1, Persiaran Southkey 1, Southkey, 80150 Johor Bahru, Johor Darul Ta'zim",
            "7, Persiaran Medini Utara 3, 79100 Iskandar Puteri, Johor Darul Ta'zim",
            "Danga Bay"};

            String[] destinationDisplay = {"Jalan Wong Ah Fook",
            "City Square Mall", "Mid Valley Southkey", "LegoLand", "Danga Bay"};

            String[] distances = {"", "", "", "", ""};
            String[] times = {"", "", "", "", ""};

            for(int i=0; i<=4; i++) {
                String[] Result = getRouteData(Postcode, destinations[i]);

                int distance = Integer.parseInt(Result[1]);
                distance /= 1000;
                distances[i] = String.format("%dkm", distance);

                int secIndex = Result[0].indexOf('s');
                Result[0] = Result[0].substring(0, secIndex);

                float time = Integer.parseInt(Result[0]);
                int days = 0;
                time /= 3600;

                if(time > 24) {while(time>24) {time-=24; days++;}}

                if(days>1) times[i] = String.format("%d days %.0f hours", days, time);
                else if(days==1) times[i] = String.format("1 day %.0f hours", days, time);
                else if(time>=1) times[i] = String.format("%.1f hours", time);
                else times[i] = String.format("%.0f mins", time * 60);

            }

            final ObservableList<Routes> data = FXCollections.observableArrayList(
                    new Routes(destinationDisplay[0], distances[0], times[0]),
                    new Routes(destinationDisplay[1], distances[1], times[1]),
                    new Routes(destinationDisplay[2], distances[2], times[2]),
                    new Routes(destinationDisplay[3], distances[3], times[3]),
                    new Routes(destinationDisplay[4], distances[4], times[4]));

            routeTable.setItems(data);

            Hyperlink hyperlink = new Hyperlink();
            final String URLPostcode = Postcode.replaceAll(",", "").replaceAll(" ", "+");

            hyperlink.setOnAction(event -> {
                try {
                    Desktop.getDesktop().browse(new URI("https://www.google.com/maps/dir/" +
                            URLPostcode));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });

            hyperlink.setText("Google Maps ⤴");

            hyperlink.setStyle("-fx-font-size: 15.5px;");
            hyperlink.setPrefHeight(36);
            hyperlink.setMaxHeight(36);
            hyperlink.setMinHeight(36);
            GMapsLink.getChildren().add(hyperlink);


        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void getUpdates() throws IOException, InterruptedException {
        Document doc = Jsoup.connect("https://www.straitstimes.com/tags/causeway").get();
        Elements headlines = doc.getElementsByClass("font-header-sm-semibold");
        Elements urls = doc.getElementsByClass("select-none gap-x-04 text-primary flex flex-nowrap items-start tablet:left-divider card basis-full");


        ArrayList<String> outHeadlines = new ArrayList<>();
        ArrayList<String> outURLs = new ArrayList<>();

        for(int count=0; count<headlines.size(); count++) {
            String out = headlines.get(count).text();
            String link = urls.get(count).attr("abs:href");

            outHeadlines.add(out);
            outURLs.add(link);
        }

        for(int count=0; count <= 4; count++) {
            Hyperlink link = new Hyperlink(outURLs.get(count));
            link.setText("  " + outHeadlines.get(count));

            link.setStyle("-fx-font-size: 15.5px;");
            link.setPrefHeight(36);
            link.setMaxHeight(36);
            link.setMinHeight(36);
            link.setPrefWidth(900);
            VBox.setVgrow(link, Priority.ALWAYS);


            int finalCount = count;
            link.setOnAction(event -> {
                try {
                    Desktop.getDesktop().browse(new URI(outURLs.get(finalCount)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                } catch (URISyntaxException e) {
                    throw new RuntimeException(e);
                }
            });

            Updates.getChildren().add(link);
        }

        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.getOptions().setCssEnabled(false);
        webClient.getOptions().setUseInsecureSSL(true);

        HtmlPage page = webClient.getPage("https://www.motorist.sg/petrol-prices?srsltid=AfmBOoqnlxSNjWiyxM8H2P3ar7lgGYKEOkpl94KlQpMglvzwCzNLuG0Q");

        DomElement closeBtn = page.getElementById("media-asset-popup-fullscreen");
        closeBtn.click();
        String pageSource = page.asXml();
        Document SG = Jsoup.parse(pageSource);
        Elements rows = SG.getElementsByClass("text-center");


        page = webClient.getPage("https://www.motorist.my/petrol-prices");
        closeBtn = page.getElementById("media-asset-popup-fullscreen");
        closeBtn.click();
        pageSource = page.asXml();
        Document MY = Jsoup.parse(pageSource);
        Elements required = MY.getElementsByClass(" d-flex justify-content-between p-3 font-chart");


        String price = "";

        if(required.get(1).child(0).text().equals("RON 97")) {
            price = required.get(1).child(1).text();
        }

        String[] prices = new String[5];

        for (int i = 3; i < 9; i++) {
            Elements rowItem = rows.get(i).getElementsByTag("td");

            if (rowItem.get(0).text().equals("95")) {
                for (int count = 1; count < rowItem.size(); count++) {
                    prices[count - 1] = "S$ " + rowItem.get(count).text().substring(1);
                }
            }
        }

        double actualPrice = Double.parseDouble(price.replaceAll("RM ", ""));


        HttpClient MYClient = HttpClient.newHttpClient();
        HttpRequest getMY = HttpRequest.newBuilder()
                .uri(URI.create("https://api.exchangeratesapi.io/v1/latest?access_key=aab65faca79a2abd4c8761760eb607bc&symbols=SGD,MYR&format=1"))
                .GET()
                .build();

        HttpResponse<String> MYResponse = MYClient.send(getMY, HttpResponse.BodyHandlers.ofString());
        JSONObject MYResponseJSON = new JSONObject(MYResponse.body());

        //JSONObject rates = MYResponseJSON.getJSONObject("rates");

        //double MYRSGD = rates.getDouble("SGD") / rates.getDouble("MYR");
        double MYRSGD = 0.31;
        double SGDMYR = (1/MYRSGD);

        price = String.format("S$ %.2f", actualPrice * MYRSGD);

        final ObservableList<PetrolPrices> data = FXCollections.observableArrayList(
                new PetrolPrices("SG", prices[0], prices[1], prices[2], prices[3]),
                new PetrolPrices("MY", price, price, price, price)

        );

        petrolPriceTable.setItems(data);


        SGDIn.textProperty().addListener((observable, oldValue, newValue) -> {
            String textIn = SGDIn.getText();

            try {
                double amount = Double.parseDouble(textIn);
                MYRIn.setText(String.format("%.2f", amount * SGDMYR));
            }
            catch(Exception e) {
                MYRIn.setPromptText("Enter a number");
            }

            if(textIn.equals("")) {
                MYRIn.setText("");
            }

        });

        MYRIn.textProperty().addListener((observable, oldValue, newValue) -> {
            String textIn = MYRIn.getText();

            try {
                double amount = Double.parseDouble(textIn);
                SGDIn.setText(String.format("%.2f", amount * MYRSGD));
            }
            catch(Exception e) {
                SGDIn.setPromptText("Enter a number");
            }

            if(textIn.equals("")) {
                SGDIn.setText("");
            }

        });

    }


    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        Country.setCellValueFactory(new PropertyValueFactory<PetrolPrices, String>("Country"));
        Esso.setCellValueFactory(new PropertyValueFactory<PetrolPrices, String>("Esso"));
        Shell.setCellValueFactory(new PropertyValueFactory<PetrolPrices, String>("Shell"));
        SPC.setCellValueFactory(new PropertyValueFactory<PetrolPrices, String>("SPC"));
        Caltex.setCellValueFactory(new PropertyValueFactory<PetrolPrices, String>("Caltex"));

        Location.setCellValueFactory(new PropertyValueFactory<Routes, String>("Location"));
        Distance.setCellValueFactory(new PropertyValueFactory<Routes, String>("Distance"));
        TimeDestination.setCellValueFactory(new PropertyValueFactory<Routes, String>("TimeDestination"));

    }

    public String[] getRouteData(String PostcodeOrigin, String Destination) throws IOException, InterruptedException {
            JSONObject MYResponseJSON, routeData;
            String time, distance;

            String outString = "https://routes.googleapis.com/directions/v2:computeRoutes";


            String body = String.format("""
                        {
                            "origin": {
                              "address": "%s"
                            },
                            "destination": {
                              "address": "%s"
                            },
                            "travelMode": "DRIVE",
                            "routingPreference": "TRAFFIC_AWARE",
                            "computeAlternativeRoutes": false,
                            "routeModifiers": {
                              "avoidTolls": false,
                              "avoidHighways": false,
                              "avoidFerries": false
                            },
                            "languageCode": "en-US",
                            "units": "METRIC"
                        }
                    """, PostcodeOrigin, Destination);

            HttpClient mapsClient = HttpClient.newHttpClient();
            HttpRequest getMatrixCheckPoint = HttpRequest.newBuilder()
                    .uri(URI.create(outString))
                    .header("Content-Type", "application/json")
                    .header("X-Goog-Api-Key", API_KEY_GMAPS)
                    .header("X-Goog-FieldMask", "routes.duration,routes.distanceMeters")
                    .POST(HttpRequest.BodyPublishers.ofString(body))
                    .build();


            HttpResponse<String> MYResponse = mapsClient.send(getMatrixCheckPoint, HttpResponse.BodyHandlers.ofString());
            MYResponseJSON = new JSONObject(MYResponse.body());

            // Code for instructions
//                     .header("X-Goog-FieldMask", "routes.duration,routes.distanceMet" +
//                            "ers,routes.navigationInstruction")
//
//
//            JSONArray instructionsJSON = (JSONArray) MYResponseJSON.getJSONArray("navigationInstruction");
//            String[] instructions = new String[instructionsJSON.length()];
//
//            for(int i=0; i<instructionsJSON.length(); i++) {
//                JSONObject instruction = (JSONObject) instructionsJSON.get(i);
//
//                instructions[i] = instruction.getString("instructions");;
//            }
            
            routeData = (JSONObject) MYResponseJSON.getJSONArray("routes").get(0);

            time = routeData.getString("duration");
            distance = String.valueOf(routeData.getInt("distanceMeters"));

            String[] Result = {time, distance};

            return Result;
    }


    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.vsync", "false");

        Document imgPage = Jsoup.connect("https://onemotoring.lta.gov.sg/content/onemotoring/home/driving/traffic_information/traffic-cameras/woodlands.html#trafficCameras").get();

        String img1src = imgPage.getElementsByClass("card").get(0).child(1).attr("src");
        String img2src = imgPage.getElementsByClass("card").get(1).child(1).attr("src");
        String img3src = imgPage.getElementsByClass("card").get(2).child(1).attr("src");
        String img4src = imgPage.getElementsByClass("card").get(3).child(1).attr("src");
        getUpdates();
        String postcode = getJourneyData();

        String[] Tuas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "29", "30", "31", "32", "33", "34", "35", "36", "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56", "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "81"};
        String[] Woodlands = {"25", "26", "27", "28", "72", "73", "75", "76", "77", "78", "79", "80", "82"};

        for(int i=0; i<Tuas.length; i++) {
            if(postcode.substring(0, 2).equals(Tuas[i])) {
                checkpointLbl.setText("Your Nearest Checkpoint Is Tuas");
                img1lbl.setText("To Johor");
                img2lbl.setText("To Singapore");

                javafx.scene.image.Image image1 = new javafx.scene.image.Image(img1src, true);
                img1.setImage(image1);
                javafx.scene.image.Image image2 = new javafx.scene.image.Image(img2src, true);
                img2.setImage(image2);
            }

        }
        for(int i=0; i<Woodlands.length; i++) {
            if(postcode.substring(0, 2).equals(Woodlands[i])) {
                checkpointLbl.setText("Your Nearest Checkpoint Is Woodlands");
                img1lbl.setText("To Johor");
                img2lbl.setText("To Singapore");

                javafx.scene.image.Image image1 = new javafx.scene.image.Image(img3src, true);
                img1.setImage(image1);
                javafx.scene.image.Image image2 = new javafx.scene.image.Image(img4src, true);
                img2.setImage(image2);
            }
        }

        getRoute(postcode);

        FXMLLoader loader = new FXMLLoader(getClass().getResource("homePage2.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root);
        stage.setScene(scene);

        stage.show();

        stage.iconifiedProperty().addListener((obs, oldVal, newVal) -> {
            if (!newVal) { // Restored from minimize
                Platform.runLater(() -> {
                    stage.setIconified(false);
                    stage.hide();
                    stage.show();
                    stage.toFront();
                });
            }
        });
        stage.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                Platform.runLater(() -> {
                    stage.setOpacity(1.0);
                    stage.toFront();
                });
            } else {
                stage.setOpacity(0.99); // forces compositor refresh on Windows
            }
        });
        stage.iconifiedProperty().addListener((obs, wasMinimized, isNowMinimized) -> {
            if (!isNowMinimized) {
                // Defensive: restore if invisible after minimize
                if (!stage.isShowing() || !stage.isFocused()) {
                    Platform.runLater(() -> {
                        try {
                            stage.show();
                            stage.toFront();
                            stage.requestFocus();
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    });
                }
            }
        });
        stage.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
            if (isFocused && !stage.isShowing()) {
                stage.show();
                stage.toFront();
            }
        });

        Platform.runLater(() -> {
            // Force a layout pulse + scene graph re-render
            stage.sizeToScene();
            stage.centerOnScreen();

            final Scene scene1 = stage.getScene();
            if (scene1 != null && scene1.getRoot() != null) {
                scene1.getRoot().applyCss();
                scene1.getRoot().requestLayout();
            }
        });




    }
}
