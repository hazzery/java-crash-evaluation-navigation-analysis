class Crash {
    /**
     * Creates a new crash object
     *
     * @param {number} latitude
     * @param {number} longitude
     * @param {int} severity
     */
    constructor(latitude, longitude, severity) {
        this.lat = latitude;
        this.lng = longitude;
        this.value = severity;
    }
}