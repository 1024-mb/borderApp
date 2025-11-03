package com.fuelprices.fuelprices;

import javafx.application.Application;

import javafx.application.Platform;
import javafx.beans.binding.Bindings;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.awt.*;
import java.io.*;
import java.net.*;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import java.util.ArrayList;

import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;
import javafx.stage.Stage;
import org.htmlunit.BrowserVersion;
import org.htmlunit.WebClient;
import org.htmlunit.html.DomElement;
import org.htmlunit.html.HtmlPage;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class homePageController extends Application implements Initializable {
    private final String API_KEY_GMAPS = System.getenv("API_KEY_GMAPS");
    private final String API_KEY_TOMTOM = System.getenv("API_KEY_TOMTOM");
    private final String API_KEY_DATAMALL = System.getenv("API_KEY_DATAMALL");
    private final String API_KEY_OPENMAP = System.getenv("API_KEY_OPENMAP");;

    @FXML
    public BorderPane homePane;
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
    private ScrollPane scrollPane;
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
    private Label img3lbl;
    @FXML
    private Label img4lbl;
    @FXML
    private Label checkpointLbl;
    @FXML
    private Hyperlink originLink;
    @FXML
    private ToggleGroup checkpointToggle;
    @FXML
    private RadioButton woodlandsRoute;
    @FXML
    private RadioButton tuasRoute;



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

    public ObservableList<Routes> getRoute(String Postcode) {
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
                else if(time>1) times[i] = String.format("%.1f hours", time);
                else if(time<1.1 && time>=1.0) times[i] = "1 hour";
                else times[i] = String.format("%.0f mins", time * 60);

            }

            final ObservableList<Routes> data = FXCollections.observableArrayList(
                    new Routes(destinationDisplay[0], distances[0], times[0]),
                    new Routes(destinationDisplay[1], distances[1], times[1]),
                    new Routes(destinationDisplay[2], distances[2], times[2]),
                    new Routes(destinationDisplay[3], distances[3], times[3]),
                    new Routes(destinationDisplay[4], distances[4], times[4]));

            return data;

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void handleClick(ActionEvent event) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("settingsPage.fxml"));
        Parent root = loader.load();

        // Get the controller linked to the FXML

        // Get stage from event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(new Scene(root));
        stage.show();
    }

    public class Update {
        ObservableList<PetrolPrices> petrolPriceData;
        double MYRSGD, SGDMYR;
        String[] updates = new String[100];
    }

    public Update getUpdates() throws IOException, InterruptedException {
        Update update = new Update();

        final HttpClient generalClient = HttpClient.newHttpClient();
        HttpRequest getUpdates = HttpRequest.newBuilder()
                .uri(URI.create("https://datamall2.mytransport.sg/ltaodataservice/TrafficIncidents"))
                .header("AccountKey", "DYcp2xF6QNKde4zceePCkw==")
                .GET()
                .build();

        HttpResponse<String> UpdatesResponse = generalClient.send(getUpdates, HttpResponse.BodyHandlers.ofString());
        JSONObject UpdatesJson = new JSONObject(UpdatesResponse.body());
        JSONArray jsonArray = UpdatesJson.getJSONArray("value");
        //["BKE", "SLE", "CTE", "TPE", "AYE", "PIE", "Tuas"]

        JSONObject current;
        String msg;
        int maxIndex = Math.min(jsonArray.length(), 13);

        for(int i = 0; i < maxIndex; i++) {
            current = (JSONObject) jsonArray.get(i);
            msg = current.getString("Message");
            System.out.println(msg);

            update.updates[i] = "    " + msg;

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



        HttpRequest getMY = HttpRequest.newBuilder()
                .uri(URI.create("https://api.exchangeratesapi.io/v1/latest?access_key=aab65faca79a2abd4c8761760eb607bc&symbols=SGD,MYR&format=1"))
                .GET()
                .build();

        HttpResponse<String> MYResponse = generalClient.send(getMY, HttpResponse.BodyHandlers.ofString());
        JSONObject MYResponseJSON = new JSONObject(MYResponse.body());

        try {
            JSONObject rates = MYResponseJSON.getJSONObject("rates");
            update.MYRSGD = rates.getDouble("SGD") / rates.getDouble("MYR");
        } catch (JSONException e) {
            update.MYRSGD = 0.31;
            e.printStackTrace();
        }
        update.SGDMYR = (1/update.MYRSGD);

        price = String.format("S$ %.2f", actualPrice * update.MYRSGD);

        final ObservableList<PetrolPrices> data = FXCollections.observableArrayList(
                new PetrolPrices("SG", prices[0], prices[1], prices[2], prices[3]),
                new PetrolPrices("MY", price, price, price, price)

        );

        //petrolPriceTable.setItems(data);
        update.petrolPriceData = data;

        return update;
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

            routeData = (JSONObject) MYResponseJSON.getJSONArray("routes").get(0);

            time = routeData.getString("duration");
            distance = String.valueOf(routeData.getInt("distanceMeters"));

            String[] Result = {time, distance};

            return Result;
    }

    public class CheckpointData {
        String img1src, img2src, img3src, img4src, img1lbl, img2lbl, img3lbl, img4lbl;
        String checkpointLbl;
        String postcode, originLink;
        ObservableList<Routes> result;
        Hyperlink hyperlink;
        Update update;
        javafx.scene.image.Image image1, image2, image3, image4;
    }

    @Override
    public void start(Stage stage) throws Exception {
        System.setProperty("prism.order", "sw");
        System.setProperty("prism.text", "t2k");
        System.setProperty("prism.vsync", "false");
        System.setProperty("javafx.animation.fullspeed", "true");

        Task<CheckpointData> task = new Task<>() {
            @Override
            protected CheckpointData call() throws Exception {
                petrolPriceTable.setPlaceholder(new Label("Loading....."));
                routeTable.setPlaceholder(new Label("Loading....."));

                CheckpointData checkpoint = new CheckpointData();
                Document imgPage = Jsoup.connect("https://onemotoring.lta.gov.sg/content/onemotoring/home/driving/traffic_information/traffic-cameras/woodlands.html#trafficCameras").get();

                String img1src = imgPage.getElementsByClass("card").get(0).child(1).attr("src");
                String img2src = imgPage.getElementsByClass("card").get(1).child(1).attr("src");
                String img3src = imgPage.getElementsByClass("card").get(2).child(1).attr("src");
                String img4src = imgPage.getElementsByClass("card").get(3).child(1).attr("src");

                String postcode = getJourneyData();
                checkpoint.postcode = postcode;
                checkpoint.originLink = (String.format("From %s, Singapore", postcode));

                String[] Tuas = {"01", "02", "03", "04", "05", "06", "07", "08", "09", "10", "11", "12", "13", "14",
                        "15", "16", "17", "18", "19", "20", "21", "22", "23", "24", "29", "30", "31", "32", "33", "34", "35", "36",
                        "37", "38", "39", "40", "41", "42", "43", "44", "45", "46", "47", "48", "49", "50", "51", "52", "53", "54", "55", "56",
                        "57", "58", "59", "60", "61", "62", "63", "64", "65", "66", "67", "68", "69", "70", "71", "81"};
                String[] Woodlands = {"25", "26", "27", "28", "72", "73", "75", "76", "77", "78", "79", "80", "82"};

                for (int i = 0; i < Tuas.length; i++) {

                    if (postcode.substring(0, 2).equals(Tuas[i])) {
                        checkpoint.checkpointLbl = ("Your Nearest Checkpoint is Tuas");
                        checkpoint.img1lbl = ("Towards Johor (View From Second Link)");
                        checkpoint.image1 = new javafx.scene.image.Image(img1src, true);


                        checkpoint.image2 = new javafx.scene.image.Image(img2src, true);
                        checkpoint.img2lbl = ("Towards Johor (Tuas Checkpoint)");

                        Document ayeImg = Jsoup.connect("https://onemotoring.lta.gov.sg/content/onemotoring/home/driving/traffic_information/traffic-cameras/aye.html#trafficCameras").get();
                        checkpoint.img3src = ayeImg.getElementsByClass("card").get(0).child(1).attr("src");
                        checkpoint.image3 = new javafx.scene.image.Image(img3src, true);

                        checkpoint.img4src = ayeImg.getElementsByClass("card").get(3).child(1).attr("src");
                        checkpoint.image4 = new javafx.scene.image.Image(img4src, true);

                        checkpoint.img3lbl = ("Towards Johor (After Tuas West Road)");
                        checkpoint.img4lbl = ("Towards Singapore (From Jln Ahmad Ibrahim)");
                    }
                }
                for (int i = 0; i < Woodlands.length; i++) {
                    if (postcode.substring(0, 2).equals(Woodlands[i])) {
                        checkpoint.checkpointLbl = ("Your Nearest Checkpoint is Woodlands");

                        //checkpoint.img1src = img3src;
                        checkpoint.image1 = new javafx.scene.image.Image(img3src, true);
                        checkpoint.img1lbl = ("Towards Johor (From Woodland's Causeway)");

                        // set the one towards singapore as the last image, with the johor ones before it
                        checkpoint.image4 = new javafx.scene.image.Image(img4src, true);
                        checkpoint.img4lbl = ("Towards Singapore (From Woodland's Checkpoint)");

                        Document bkeImg = Jsoup.connect("https://onemotoring.lta.gov.sg/content/onemotoring/home/driving/traffic_information/traffic-cameras/bke.html#trafficCameras").get();
                        String bkeImg1 = bkeImg.getElementsByClass("card").get(3).child(1).attr("src");
                        String bkeImg2 = bkeImg.getElementsByClass("card").get(5).child(1).attr("src");

                        checkpoint.image2 = new javafx.scene.image.Image(bkeImg1, true);
                        checkpoint.img2lbl = ("Towards Johor (Exit 5 To KJE)");

                        checkpoint.image3 = new javafx.scene.image.Image(bkeImg2, true);
                        checkpoint.img3lbl = ("Towards Johor (Woodlands Flyover)");

                    }
                }
                checkpoint.result = getRoute(postcode);
                Hyperlink hyperlink = new Hyperlink();
                final String URLPostcode = postcode.replaceAll(",", "").replaceAll(" ", "+");

                hyperlink.setOnAction(event -> {
                    try {
                        Desktop.getDesktop().browse(new URI("https://www.google.com/maps/dir/" +
                                URLPostcode + " " + "Singapore"));
                    } catch (IOException k) {
                        throw new RuntimeException(k);
                    } catch (URISyntaxException k) {
                        throw new RuntimeException(k);
                    }
                });

                hyperlink.setText("Google Maps ⤴");

                hyperlink.setStyle("-fx-font-size: 15.5px;");
                hyperlink.setPrefHeight(36);
                hyperlink.setMaxHeight(36);
                hyperlink.setMinHeight(36);

                checkpoint.hyperlink = hyperlink;

                Update update;
                try {
                    update = getUpdates();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                } catch (InterruptedException ex) {
                    throw new RuntimeException(ex);
                }
                checkpoint.update = update;

                return checkpoint;
            }

        };
        task.setOnFailed(e -> {
            System.out.println("Task failed: " + task.getException());
            task.getException().printStackTrace();
        });
        task.setOnSucceeded(e -> {

            stage.setTitle("BorderApp");

            stage.iconifiedProperty().addListener((obs, oldVal, newVal) -> {
                if (!newVal) { // Restored from minimize
                    Platform.runLater(() -> {
                        stage.setIconified(false);
                        stage.hide();
                        stage.show();
                        FxStabilizer.stabilize(stage);
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
                                FxStabilizer.stabilize(stage);
                                stage.toFront();
                                stage.requestFocus();
                            } catch (Exception z) {
                                z.printStackTrace();
                            }
                        });
                    }
                }
            });
            stage.focusedProperty().addListener((obs, wasFocused, isFocused) -> {
                if (isFocused && !stage.isShowing()) {
                    stage.show();
                    FxStabilizer.stabilize(stage);
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

            checkpointToggle.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
                Document imgPage = null;
                try {
                    imgPage = Jsoup.connect("https://onemotoring.lta.gov.sg/content/onemotoring/home/driving/traffic_information/traffic-cameras/woodlands.html#trafficCameras").get();
                } catch (IOException z) {
                    throw new RuntimeException(z);
                }

                String img1src = imgPage.getElementsByClass("card").get(0).child(1).attr("src");
                String img2src = imgPage.getElementsByClass("card").get(1).child(1).attr("src");
                String img3src = imgPage.getElementsByClass("card").get(2).child(1).attr("src");
                String img4src = imgPage.getElementsByClass("card").get(3).child(1).attr("src");

                if (newValue != null && newValue instanceof RadioButton) {
                    String selected = ((RadioButton) newValue).getText();

                    if(selected.equals("Tuas Route")) {
                        javafx.scene.image.Image imageSelected1 = new javafx.scene.image.Image(img1src, true);
                        img1.setImage(imageSelected1);
                        img1lbl.setText("Towards Johor (View From Second Link)");

                        javafx.scene.image.Image imageSelected2 = new javafx.scene.image.Image(img2src, true);
                        img2.setImage(imageSelected2);
                        img2lbl.setText("Towards Johor (Tuas Checkpoint)");

                        Document ayeImg = null;
                        try {
                            ayeImg = Jsoup.connect("https://onemotoring.lta.gov.sg/content/onemotoring/home/driving/traffic_information/traffic-cameras/aye.html#trafficCameras").get();
                        } catch (IOException z) {
                            throw new RuntimeException(z);
                        }
                        String ayeImg1 = ayeImg.getElementsByClass("card").get(0).child(1).attr("src");
                        String ayeImg2 = ayeImg.getElementsByClass("card").get(3).child(1).attr("src");

                        javafx.scene.image.Image imageSelected3 = new javafx.scene.image.Image(ayeImg1, true);
                        img3.setImage(imageSelected3);
                        img3lbl.setText("Towards Johor (After Tuas West Road)");

                        img4lbl.setText("Towards Singapore (From Jln Ahmad Ibrahim)");
                        javafx.scene.image.Image imageSelected4 = new javafx.scene.image.Image(ayeImg2, true);
                        img4.setImage(imageSelected4);
                    }
                    else {
                        javafx.scene.image.Image imageSelected1 = new javafx.scene.image.Image(img3src, true);
                        img1.setImage(imageSelected1);
                        img1lbl.setText("Towards Johor (From Woodland's Causeway)");

                        // set the one towards singapore as the last image, with the johor ones before it
                        javafx.scene.image.Image imageSelected2 = new javafx.scene.image.Image(img4src, true);
                        img4.setImage(imageSelected2);
                        img4lbl.setText("Towards Singapore (From Woodland's Checkpoint)");

                        Document bkeImg = null;
                        try {
                            bkeImg = Jsoup.connect("https://onemotoring.lta.gov.sg/content/onemotoring/home/driving/traffic_information/traffic-cameras/bke.html#trafficCameras").get();
                        } catch (IOException z) {
                            throw new RuntimeException(z);
                        }
                        String bkeImg1 = bkeImg.getElementsByClass("card").get(3).child(1).attr("src");
                        String bkeImg2 = bkeImg.getElementsByClass("card").get(5).child(1).attr("src");

                        javafx.scene.image.Image imageSelected3 = new javafx.scene.image.Image(bkeImg1, true);
                        img2.setImage(imageSelected3);
                        img2lbl.setText("Towards Johor (Exit 5 To KJE)");

                        javafx.scene.image.Image imageSelected4 = new javafx.scene.image.Image(bkeImg2, true);
                        img3.setImage(imageSelected4);
                        img3lbl.setText("Towards Johor (Woodlands Flyover)");
                    }

                }
            });

            CheckpointData data = task.getValue();

            routeTable.setItems(data.result);
            routeTable.setFixedCellSize(35);


            originLink.setText(data.originLink);

            GMapsLink.getChildren().add(data.hyperlink);
            checkpointLbl.setText(data.checkpointLbl);

            if(data.checkpointLbl.toLowerCase().contains("tuas")) {
                checkpointToggle.selectToggle(tuasRoute);
            }
            else {
                checkpointToggle.selectToggle(woodlandsRoute);
            }

            for(String update: data.update.updates) {
                if(!(update==null)) {
                    String prefix = "";
                    
                    if (update.toLowerCase().contains("traffic")) {
                        prefix = " \uD83D\uDEA6  ";
                    } else if(update.toLowerCase().contains("roadworks")) {
                        prefix = " \uD83D\uDEA7  ";
                    } else if(update.toLowerCase().contains("breakdown")) {
                        prefix = " \uD83D\uDEE0\uFE0F  ";
                    } else if(update.toLowerCase().contains("accident")) {
                        prefix = " ⛐  ";
                    }
                    
                    Label label1 = new Label(prefix + update.substring(10, 15) + "  " + update.substring(15));
                    label1.setStyle("-fx-font-size: 14.5px; -fx-margin-bottom: 3px;");
                    Updates.getChildren().add(label1);
                }
            }

            img1.setImage(data.image1);
            img2.setImage(data.image2);
            img3.setImage(data.image3);
            img4.setImage(data.image4);

            Update update = data.update;
            petrolPriceTable.setItems(update.petrolPriceData);
            petrolPriceTable.setFixedCellSize(35);


            SGDIn.textProperty().addListener((observable, oldValue, newValue) -> {
                String textIn = SGDIn.getText();

                try {
                    double amount = Double.parseDouble(textIn);
                    MYRIn.setText(String.format("%.2f", amount * update.SGDMYR));
                }
                catch(Exception z) {
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
                    SGDIn.setText(String.format("%.2f", amount * update.MYRSGD));
                }
                catch(Exception z) {
                    SGDIn.setPromptText("Enter a number");
                }

                if(textIn.equals("")) {
                    SGDIn.setText("");
                }

            });

        });

        new Thread(task).start();

    }
}
