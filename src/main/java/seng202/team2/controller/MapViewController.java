package seng202.team2.controller;

import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import netscape.javascript.JSObject;

import seng202.team2.database.Dao;
import seng202.team2.database.LocationDao;
import seng202.team2.models.Location;
import seng202.team2.models.Crashes;

import java.util.Objects;

/**
 * Map view controller class for the application.
 * Handles displaying the map and heatmap.
 * Adapted from Special Lab: Working with Interactive Map APIs
 *
 * @author Louis Hobson
 * @author Findlay Royds
 * @see <a href="https://learn.canterbury.ac.nz/pluginfile.php/6629561/mod_folder/content/0/Resource-for-maps-APIs.pdf">
 * SENG202 Resources for working with Interactive Map APIs</a>
 */
public class MapViewController {

    @FXML
    private WebView webView;

    private WebEngine webEngine;

    private MainController mainController;

    /**
     * Initialise the map view
     *
     * @param mainController The mainController of the application. Needed to display the loading screen
     */
    void init(MainController mainController) {
        this.mainController = mainController;
        initMap();
    }
    public static class JavaBridge
    {
        public void log(String text)
        {
            System.out.println(text);
        }
    }
    // Maintain a strong reference to prevent garbage collection:
    // https://bugs.openjdk.java.net/browse/JDK-8154127
    private final JavaBridge bridge = new JavaBridge();

    /**
     * Initialises the WebView loading in the appropriate html and initialising important communicator
     * objects between Java and Javascript
     */
    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load(Objects.requireNonNull(MapViewController.class.getResource("/map/map.html")).toExternalForm());

        webEngine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    // if javascript loads successfully
                    if (newState == Worker.State.SUCCEEDED) {
                        webEngine.executeScript("initHeatmap();");
                        addAllCrashMarkers();
                        mainController.getLoadingScreen().hide();
                    }
                });
                //This redirects JavaScript's `console.log` to Java `System.out.println`
                JSObject window = (JSObject) webEngine.executeScript("window");
                window.setMember("java", bridge);
                webEngine.executeScript("console.log = function(message){java.log(message);};");
    }

    /**
     * Adds all the crashes into the heatmap layer
     */
    public void addAllCrashMarkers()  {
        clearMarkers();

        // load the crashes onto the map
        StringBuilder markerString = new StringBuilder();
        for (Crash crash : Crashes.getCrashes()) {
            if (crash != null) {
                float newLong = (float) crash.longitude();
                if (newLong < 0) {
                    newLong += 360;
                }
                markerString.append(String.format("preMarker(%f, %f);", (float) crash.latitude(), newLong));
            }
        }

        webEngine.executeScript(markerString.toString());

        postMarkers();
    }

    /**
     * Tells the WebEngine to clear all points from the heatmap
     */
    private void clearMarkers() {
        webEngine.executeScript("clearMarkers();");
    }

    /**
     * Tells the WebEngine to update the intensity of the heatmap
     */
    private void postMarkers() {
        webEngine.executeScript("postMarkers();");
    }

    /**
     * Initialise the map
     *
     * @param mainController The mainController of the application. Needed to display the loading screen
     */
    void init(MainController mainController) {
        initMap();
        this.mainController = mainController;
    }
}

