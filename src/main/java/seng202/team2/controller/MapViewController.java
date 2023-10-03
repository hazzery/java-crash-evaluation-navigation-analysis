package seng202.team2.controller;


import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import javafx.util.Duration;
import netscape.javascript.JSObject;

import seng202.team2.models.Crash;
import seng202.team2.models.Crashes;

/**
 * Map view controller class for the application.
 * Handles displaying the map and point markers.
 * Adapted from Special Lab: Working with Interactive Map APIs
 *
 * @author Louis Hobson
 * @author Findlay Royds
 */


public class MapViewController {
    
    @FXML
    private WebView webView;
    
    private WebEngine webEngine;
    private JSObject javaScriptConnector;

    private MainController mainController;
    
    /**
     * Initialises the WebView loading in the appropriate html and initialising important communicator
     * objects between Java and Javascript
     */
    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load(MapViewController.class.getResource("/map.html").toExternalForm());
        
        webEngine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    // if javascript loads successfully
                    if (newState == Worker.State.SUCCEEDED) {
                        // set our bridge object
                        JSObject window = (JSObject) webEngine.executeScript("window");
                        //window.setMember("javaScriptBridge", javaScriptBridge);
                        // get a reference to the js object that has a reference to the js methods we need to use in java
                        javaScriptConnector = (JSObject) webEngine.executeScript("jsConnector");
                        // call the javascript function to initialise the map
                        //javaScriptConnector.call("initMap");
                        webEngine.executeScript(
                                "doHeatmap();"
                        );
                        addAllCrashMarkers();
                    }
                });
    }

    /**
     * Adds all the crashes as markers on the map and pre-calculates the clustering
     * Uses threading to display loading updates on the application
     */
    public void addAllCrashMarkers()  {
        // Clear markers being displayed on the screen
        clearMarkers();

        // load the crashes onto the map
        StringBuilder markerString = new StringBuilder();
        for (Crash crash : Crashes.getCrashes()) {
            if (crash != null) {
                markerString.append(String.format("preMarker(%f, %f);", (float) crash.latitude(), (float) crash.longitude()));
            }
        }
        webEngine.executeScript(markerString.toString());

        postMarkers();
        mainController.hideLoadingView();
    }
    
    /**
     * Parse in all the crashes into javascript
     */
    private void preMarker(Crash crash) {
        webEngine.executeScript(
                String.format("preMarker(%f, %f);", (float)crash.latitude(), (float)crash.longitude())
        );
    }

    /**
     * Tells the WebEngine to clear all the markers
     */
    private void clearMarkers() {
        webEngine.executeScript("clearMarkers();");
    }
    
    /**
     * Tells javascript to sort through all the regions
     */
    private void postMarkers() {
        webEngine.executeScript("postMarkers();");
    }

    /**
     * Init
     */
    void init(MainController mainController) {
        initMap();
        this.mainController = mainController;
    }
}

