/**
 * Main script for displaying data on the heatmap.
 * Exposes functions to manipulate the heatmap to the mapViewController class.
 *
 * @author Findlay Royds
 * @author Harrison Parkes
 */

let map;
let heatmapLayer;
let heatmapData = {max: 10000, data: []};

/**
 * Manually defined values to adjust the intensity of the heatmap at different zoom levels
 * @type {Record<int, int>}
 */
const heatmapMaxValues = {
    5: 10000,
    6: 6000,
    7: 5000,
    8: 3000,
    9: 2000,
    10: 1000,
    11: 500,
    12: 200,
    13: 90,
    14: 60,
    15: 40,
    16: 35,
    17: 25,
    18: 20
};

// Prevent the context menu from opening when the user right-clicks on the map.
document.oncontextmenu = event => event.preventDefault();

/**
 * Updates the heatmap with the new crash locations
 * @param {int[]} ids - The ids of the crashes to display
 */
function displayPoints(ids) {
    heatmapData.data = CrashManager.getFromIds(ids);
    refreshIntensity();
}

/**
 * Initialises the map and heatmap layers with default configuration.
 * Connects the leaflet zoom-end event to adjusting the intensity of the heatmap.
 * @returns {void}
 */
function initHeatmap() {
    // Configuration for the heatmap
    const cfg = {
        "radius": 20,
        "maxOpacity": .75,
        "minOpacity": .0,
        "scaleRadius": false,
        "useLocalExtrema": false,
        "latField": 'lat',
        "lngField": 'lng',
        "valueField": 'count',
        "blur": 1.0,
        "gradient": {0: 'green', 0.3: 'yellow', 0.75: 'orange', 1: 'red'}
    };

    // Create the heatmap layer using the defined configuration
    heatmapLayer = new HeatmapOverlay(cfg);

    // Create the leaflet map using the UC tile-server for the baseLayer
    const mapOptions = {
        center: [-44.0, 171.0],
        zoom: 5,
        minZoom: 5,
        maxZoom: 18,
        layers: [heatmapLayer],
        maxBounds: [[-60.0, 140], [-15.0, 205]]
    };

    map = new L.map('map', mapOptions);

    new L.TileLayer('https://tile.csse.canterbury.ac.nz/hot/{z}/{x}/{y}.png', { // UCs tilemap server
        attribution: '© OpenStreetMap contributors<br>Served by the University of Canterbury'
    }).addTo(map)

    // Update the max value of the heatmap when the map has been zoomed
    map.on('zoomend', function () {
        refreshIntensity();
    });
}

/**
 * Calculate the intensity config value for the heatmap based off the zoom level
 * and the number of crashes being shown.
 * @returns {void}
 */
function refreshIntensity() {
    heatmapData.max = heatmapMaxValues[map.getZoom()] * (heatmapData.data.length / 800000);
    heatmapLayer.setData(heatmapData);
}