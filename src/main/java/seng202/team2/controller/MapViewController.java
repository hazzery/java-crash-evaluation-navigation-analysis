package seng202.team2.controller;



import com.sun.javafx.webkit.WebConsoleListener;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import netscape.javascript.JSObject;

import java.net.URL;
import java.util.ResourceBundle;


/**
 * Map view controller class for the application.
 * Handles displaying the map and point markers.
 *
 * adapted from Special Lab: Working with Interactive Map APIs
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
        // Forwards console.log() output from any javascript to info log ### THIS IS BROKEN ###
        // WebConsoleListener.setDefaultListener((view, message, lineNumber, sourceId) ->
        //         System.out.println(String.format("Map WebView console log line: %d, message : %s", lineNumber, message)));

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
                    }
                });
    }

    /**
     * Init
     */
    void init() {
        initMap();
    }
}

