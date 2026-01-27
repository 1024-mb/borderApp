# Border App – Cross-Border Intelligence Application (SG–MY)

⚠️ **20-second demo:** https://youtu.be/QD_2CZFQSnc ⚠️

Border App is a Java-based desktop application that tracks and visualises cross-border data for the Singapore–Malaysia land border. It provides real-time and near-real-time insights into traffic conditions, currency exchange rates, fuel prices, road imagery, and distances to key Malaysian landmarks based on a user-registered postcode.

The application parses live road and traffic data published by the Singapore government and aggregates petrol pricing information from both government and private-sector sources to present users with an accurate and consolidated view of cross-border travel conditions.

---

## Table of Contents

- Overview
- Key Features
- Technologies Used
- Requirements
- Build Instructions
- Run Instructions
- Project Structure
- Configuration and Data
- Troubleshooting
- Contributing

---

## Overview

Border App is designed to assist commuters and travellers who regularly cross the Singapore–Malaysia land border. By consolidating multiple data sources into a single interface, the app reduces friction in travel planning and improves situational awareness before and during cross-border trips.

---

## Key Features

- Live traffic updates for the Singapore–Malaysia land border
- Road imagery and photography for situational awareness
- Currency exchange rate tracking
- Petrol price aggregation from multiple sources
- Distance calculation to common Malaysian landmarks
- User-registered postcode for personalised distance calculations
- Government data parsing for reliable travel information
- JavaFX-based desktop user interface

---

## Technologies Used

### Core
- Java (Java 11 minimum, Java 17+ recommended)
- JavaFX (UI framework)
- Maven (build and dependency management)

### Data Sources
- Singapore government traffic and road data
- Government and private-sector petrol pricing websites

### UI
- JavaFX FXML
- Embedded HTML rendering for selected views

---

## Requirements

- Java 11 or newer (Java 17 or newer recommended)
- Maven  
  - Alternatively, use the provided Maven wrapper (`mvnw` / `mvnw.cmd`)
- JavaFX SDK (required if JavaFX is not bundled with your JDK)

If running without a JavaFX-aware Maven plugin or a bundled JavaFX runtime, VM module flags must be supplied.

---

## Build Instructions

### Windows (using Maven wrapper)

```bash
.\mvnw.cmd clean package
```

### macOS / Linux (using Maven wrapper)

```bash
./mvnw clean package
```

After a successful build, the generated artifact(s) will be located in the `target/` directory.

---

## Run Instructions

### Run from an IDE

Set the following class as the main entry point and run:

```
com.fuelprices.fuelprices.Launcher
```

### Run with Maven (if `javafx-maven-plugin` is configured)

```bash
.\mvnw.cmd javafx:run
```

### Run the packaged JAR (if executable)

```bash
java -jar target/<artifact>.jar
```

### Running with a separate JavaFX SDK

If JavaFX is installed separately, add the following VM options (example):

```text
--module-path /path/to/javafx/lib --add-modules javafx.controls,javafx.fxml
```

---

## Project Structure

```text
src/main/java/
├── module-info.java
├── com/fuelprices/fuelprices/
│   ├── Launcher.java
│   ├── getStarted.java
│   ├── getStartedController.java
│   ├── homePageController.java
│   ├── settingsController.java
│   └── PetrolPrices.java

src/main/resources/
├── com/fuelprices/fuelprices/
│   ├── homePage.fxml
│   ├── getStarted.fxml
│   ├── settingsPage.fxml
│   ├── registeredPostCode.txt
│   └── webPageRender/
│       └── base.html
```

---

## Configuration and Data

The application stores the user’s registered postcode in the following file:

```
src/main/resources/com/fuelprices/fuelprices/registeredPostCode.txt
```

This file may be inspected or edited at runtime if required. It is used to calculate distances to Malaysian landmarks and personalise location-based features.

---

## Troubleshooting

- **Missing JavaFX modules**  
  Ensure your Java runtime includes JavaFX, or supply the required `--module-path` and `--add-modules` VM options.

- **FXML or resource loading failures**  
  Verify that resource paths are correct and match the package structure  
  (`com/fuelprices/fuelprices/...`).

- **Build failures**  
  Confirm Java and Maven versions meet the minimum requirements.

---

## Contributing

1. Fork the repository
2. Create a feature branch
3. Commit focused, well-scoped changes
4. Open a pull request with a clear description of the modification

Please keep changes minimal and update FXML or resource files only when necessary.
