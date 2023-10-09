let map;
var heatmapLayer;
var heatmapData = {max: 10000,data: []};

// manually defined values to adjust the intensity of the heatmap at different zoom levels
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
    18: 20,
};

// don't open context menu on right click
document.oncontextmenu = function(event) {
    event.preventDefault();
}

/**
 * Saves a crash location to an array of crash markers
 * @param region
 * @param lat latitude to place marker at
 * @param lng longitude to place marker at
 */
function preMarker(lat, lng) {
    heatmapData.data.push({lat: lat, lng: lng, count: 1});
}

/**
 * Clear markers from the map
 */
function clearMarkers() {
    heatmapData.data = [];
}

/**
 * Sets the data of the heatmap layer to the crash data
 */
function postMarkers() {
    heatmapLayer.setData(heatmapData);
}

/**
 * Initialises the map + the heatmap layer with default configuration.
 * Connects the leaflet zoomend event to adjusting the
 */
function initHeatmap() {
    // configuration for the heatmap
    var cfg = {
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

    // Create the leaflet map using the UC tileserver for the baseLayer
    var mapOptions = {
        center: [-44.0, 171.0],
        zoom: 5,
        minZoom: 5,
        maxZoom: 18,
        layers: [heatmapLayer]
    }
    map = new L.map('map', mapOptions);
    new L.TileLayer('https://tile.csse.canterbury.ac.nz/hot/{z}/{x}/{y}.png', { // UCs tilemap server
        attribution: '© OpenStreetMap contributors<br>Served by University of Canterbury'
    }).addTo(map)

    // update the max value of the heatmap when the map has been zoomed
    map.on('zoomend', function() {
        let x = -1 * map.getZoom() + 15
        setMax(heatmapMaxValues[map.getZoom()]);
    });
}


function setMax(value) {
    heatmapData.max = value;
    heatmapLayer.setData(heatmapData);
}