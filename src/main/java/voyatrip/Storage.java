package voyatrip;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Optional;

public class Storage {
    /**
     * The file path for the JSON file where the trip data is stored.
     */
    public static final String FILE_PATH = "data/voyatrip.json";

    /**
     * The isFileExistAndNonEmpty method checks if the file exists and is not empty.
     * It returns true if the file exists and has a size greater than 0, otherwise false.
     */
    public static Boolean isFileExistAndNonEmpty() {
        try {
            Path path = Paths.get(FILE_PATH);
            if (Files.exists(path)) {
                return Files.size(path) > 0;
            }
        } catch (Exception e) {
            System.out.println("Error checking file existence: " + e.getMessage());
        }
        return false;
    }

    /**
     * The load method is used to load the trip data from the file.
     * It will read the JSON file and parse it into a TripList object with all the trips.
     * It should only be called at the start of the program.
     * @return An Optional containing the TripList object if the file exists and is not empty,
     *     otherwise an empty Optional.
     */
    public static Optional<TripList> load() {
        if (!isFileExistAndNonEmpty()) {
            return Optional.empty();
        }

        try {
            String jsonStr = new String(Files.readAllBytes(Paths.get(FILE_PATH)));
            return Optional.of(TripList.fromJson(jsonStr));
        } catch (Exception e) {
            System.out.println("Error reading file: " + e.getMessage());
            return Optional.empty();
        }
    }

    /**
     * The save method is used to save the trip data to the file.
     * It will convert the TripList object into JSON format and write it to the file.
     * It should only be called when user specify or when the program is closing.
     *
     * @param trips The TripList object to save the data from.
     */
    public static void save(TripList trips) {
        Path path = Paths.get(FILE_PATH);
        try {
            // create parent directories if they do not exist
            Files.createDirectories(path.getParent());

            if (trips.toJson().isEmpty()) {
                // if the trip list is empty, delete the file
                Files.deleteIfExists(path);
                return;
            }

            // write the file to the specified path,
            Files.write(path, trips.toJson().get().toString(4).getBytes(),
                    StandardOpenOption.CREATE,
                    StandardOpenOption.TRUNCATE_EXISTING);
        } catch (Exception e) {
            System.out.println("Error writing to file: " + e.getMessage());
        }
    }
}
