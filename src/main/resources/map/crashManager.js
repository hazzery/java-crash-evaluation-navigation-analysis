// import Crash from './crash.js';

/**
 * @typedef {Object} CrashEntry
 * @property {int} id - The crash's id in the database.
 * @property {number} latitude - The crash's latitude.
 * @property {number} longitude - The crash's longitude.
 * @property {int} severity - The crash's severity.
 */


class CrashManager {
    /**
     * Stores the crash data in a hashmap using the crash ID as the key
     * @type {Map<int, Crash>}
     */
    static #allCrashes = new Map();

    /**
     * Stores the crash data in a hashmap using the crash ID as the key
     * @param {CrashEntry[]} crashData - The crash data to store
     */
    static setAllCrashes(crashData) {
        for (let crash of crashData) {
            this.#allCrashes.set(crash.id, new Crash(crash.latitude, crash.longitude, crash.severity));
        }
    }

    /**
     * Returns the crash objets corresponding to the given IDs
     * @param {int[]} ids - The IDs of the crashes to return
     * @return {Crash[]} The crashes corresponding to the given IDs
     */
    static getFromIds(ids) {
        return ids.map(id => this.#allCrashes.get(id));
    }
}



