package seng202.team2.controller;


import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

import netscape.javascript.JSObject;

import seng202.team2.models.Crash;
import seng202.team2.models.Crashes;

/**
 * Map view controller class for the application.
 * Handles displaying the map and point markers.
 * Adapted from Special Lab: Working with Interactive Map APIs
 *
 * @author Louis Hobson
 */


public class MapViewController {
    
    @FXML
    private WebView webView;
    
    private WebEngine webEngine;
    private JSObject javaScriptConnector;
    
    
    /**
     * Initialises the WebView loading in the appropriate html and initialising important communicator
     * objects between Java and Javascript
     */
    private void initMap() {
        webEngine = webView.getEngine();
        webEngine.setJavaScriptEnabled(true);
        webEngine.load(MapViewController.class.getResource("/map.html").toExternalForm());

        // webEngine.setUserStyleSheetLocation(MapViewController.class.getResource("/MarkerCluster.css").toExternalForm());
        // Forwards console.log() output from any javascript to info log ### THIS IS BROKEN ###
        // WebConsoleListener.setDefaultListener((view, message, lineNumber, sourceId) ->
        //         System.out.println(String.format("Map WebView console log line: %d, message : %s", lineNumber, message)))
        
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
                        javaScriptConnector.call("initMap");

                        addAllCrashMarkers();
                    }
                });

        //javaScriptConnector.toString();
        //
    }

    public void addAllCrashMarkers()  {
        clearMarkers();
        for (Crash crash : Crashes.getCrashes()) {
            if (crash != null) {
                preMarker(crash);
            }
        }
        postMarkers();
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
     * Clear all the crashes into javascript
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
    void init() {
        initMap();

    }
}

