package seng202.team2.controller;


import javafx.concurrent.Worker;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import seng202.team2.models.Crash;
import seng202.team2.models.Crashes;

import java.util.Objects;

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

    private MainController mainController;
    
    /**
     * Initialises the WebView loading in the appropriate html and initialising important communicator
     * objects between Java and Javascript
     */
    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load(Objects.requireNonNull(MapViewController.class.getResource("/map.html")).toExternalForm());
        
        webEngine.getLoadWorker().stateProperty().addListener(
                (ov, oldState, newState) -> {
                    // if javascript loads successfully
                    if (newState == Worker.State.SUCCEEDED) {
                        // set our bridge object
                        //window.setMember("javaScriptBridge", javaScriptBridge);
                        // get a reference to the js object that has a reference to the js methods we need to use in java
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
        long startTime = System.nanoTime();
        clearMarkers();
        System.out.println("clear Markers: " + (System.nanoTime() - startTime) / 1000000);

        startTime = System.nanoTime();
        // load the crashes onto the map
        StringBuffer markerString = new StringBuffer();
        for (Crash crash : Crashes.getCrashes()) {
            if (crash != null) {
                //markerString.append(String.format("{lat:%f,lng:%f,count:1},", (float) crash.latitude(), (float) crash.longitude()));
                //break;
                markerString.append(String.format("preMarker(%f,%f);", (float) crash.latitude(), (float) crash.longitude()));
            }
        }
        System.out.println("Building string: " + (System.nanoTime() - startTime) / 1000000);

        startTime = System.nanoTime();
        //markerString.append("]});");
        //System.out.println(markerString);
        webEngine.executeScript(markerString.toString());
        System.out.println("executing script: " + (System.nanoTime() - startTime) / 1000000);

        startTime = System.nanoTime();
        postMarkers();
        mainController.hideLoadingView();
        System.out.println("post marker: " + (System.nanoTime() - startTime) / 1000000);
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

