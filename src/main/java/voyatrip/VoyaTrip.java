package voyatrip;

import java.util.Arrays;
import java.util.logging.Logger;
import java.util.logging.Level;

import voyatrip.logic.executor.Executor;
import voyatrip.model.TripList;
import voyatrip.storage.Storage;

/**
 * This is the main class for the VoyaTrip application.
 */
public class VoyaTrip {
    static TripList trips = new TripList();

    /**
     * This method is used to check if the application is running in end user mode.
     */
    public static Boolean isEndUserMode(String[] args) {
        return !Arrays.asList(args).contains("--verbose");
    }

    /**
     * This method is used to set the logger for the application.
     * If it is the user, then the logger will be disabled.
     */
    public static void setLogger(String[] args) {
        if (isEndUserMode(args)) {
            Logger.getLogger("").setLevel(Level.OFF);
        }
    }

    public static void main(String[] args) {
        setLogger(args);
        load_json();
        run();
        save_json();
    }

    /**
     * This method is used to load the json file for the application.
     */
    public static void load_json() {
        Storage.load().ifPresent(tripList -> {
            trips = tripList;
        });
    }

    /**
     * This method is used to save the json file for the application.
     */
    public static void save_json() {
        Storage.save(trips);
    }

    /**
     * This is the main loop for the VoyaTrip application.
     * It will keep running until the user exits the application
     */
    private static void run() {
        Executor.run(trips);
    }


}
