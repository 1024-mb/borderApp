# Border App


Demo (1 minute):    https://www.youtube.com/watch?v=QD_2CZFQSnc


An app that tracks cross-border data (Singapore-Malaysia land border). Displays traffic updates, currency exchanges, road photography and distances to common Malaysian landmarks 
from a user-set Postcode.
Parses road data from the singapore government to extract travel information and display the latest updates for traffic. Extracts petrol pricing data
from government and private companies' websites to give users an accurate overview of current fuel prices.
## Requirements

- Java 11 or newer (Java 17+ recommended).
- Maven (or use the provided Maven wrapper: `mvnw` / `mvnw.cmd`).
- If running without a JavaFX-aware Maven plugin or a bundled JavaFX, you will need a JavaFX SDK and VM module flags.

## Build

- Windows (using wrapper):

  ```powershell
  .\mvnw.cmd clean package
  ```

- macOS / Linux (using wrapper):

  ```bash
  ./mvnw clean package
  ```

After a successful build, the artifact(s) will appear under `target/`.

## Run

- Run from the IDE: set `com.fuelprices.fuelprices.Launcher` as the main class and run.

- Run with Maven (if the `javafx-maven-plugin` is configured):

  ```powershell
  .\mvnw.cmd javafx:run
  ```

- Run the packaged jar (if the project produces an executable jar):

  ```powershell
  java -jar target/<artifact>.jar
  ```

If you run without the plugin and you installed JavaFX separately, add VM options (example):

```text
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

## Project structure (key files)

- `src/main/java/module-info.java` — module declaration.
- `src/main/java/com/fuelprices/fuelprices/Launcher.java` — application entry / main launcher.
- `src/main/java/com/fuelprices/fuelprices/getStarted.java` — controller/logic for the get-started flow.
- `src/main/java/com/fuelprices/fuelprices/getStartedController.java` — controller for get-started FXML.
- `src/main/java/com/fuelprices/fuelprices/homePageController.java` — controller for the home page UI.
- `src/main/java/com/fuelprices/fuelprices/settingsController.java` — controller for settings UI.
- `src/main/java/com/fuelprices/fuelprices/PetrolPrices.java` — classes handling petrol price data.

- `src/main/resources/com/fuelprices/fuelprices/homePage.fxml` — main home page FXML.
- `src/main/resources/com/fuelprices/fuelprices/getStarted.fxml` — get-started screen.
- `src/main/resources/com/fuelprices/fuelprices/settingsPage.fxml` — settings page.
- `src/main/resources/com/fuelprices/fuelprices/registeredPostCode.txt` — persisted postcode file.
- `src/main/resources/com/fuelprices/fuelprices/webPageRender/base.html` — embedded HTML template used by the app.

## Configuration & Data

- The app uses `registeredPostCode.txt` to store a registered postcode. You can inspect or edit this file at runtime if needed: `src/main/resources/com/fuelprices/fuelprices/registeredPostCode.txt`.

## Troubleshooting

- Missing JavaFX modules error: ensure your runtime includes JavaFX or add the `--module-path`/`--add-modules` VM args shown above.
- If FXML URLs or resources fail to load, confirm resource paths are correct relative to the package (`com/fuelprices/fuelprices/...`).

## Contributing

- Fork the repo, create a feature branch, and open a pull request describing your change.
- Keep changes small and focused; update resources or FXML only when necessary.



