package voyatrip.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONArray;
import org.json.JSONObject;
import voyatrip.logic.command.exceptions.InvalidArgumentValue;
import voyatrip.logic.command.exceptions.InvalidCommand;
import voyatrip.logic.command.exceptions.InvalidIndex;
import voyatrip.logic.command.exceptions.TripNotFoundException;
import voyatrip.ui.Ui;

public class TripList {
    private static Logger logger = Logger.getLogger(TripList.class.getName());
    private ArrayList<Trip> trips = new ArrayList<>();

    public void add(String name,
                    LocalDate startDate,
                    LocalDate endDate,
                    Integer numDays,
                    Integer totalBudget) throws InvalidCommand {
        logger.log(Level.INFO, "Adding new trip");
        if (isContains(name)) {
            logger.log(Level.WARNING, "Trip already exists");
            throw new InvalidArgumentValue();
        }

        Trip newTrip = new Trip(name, startDate, endDate, numDays, totalBudget);
        trips.add(newTrip);
        Ui.printAddTripMessage(newTrip.abbrInfo());
        logger.log(Level.INFO, "Finished adding new trip");
    }

    public boolean isContains(String name) {
        logger.log(Level.INFO, "Checking if trip exists");
        for (Trip trip : trips) {
            if (trip.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public Integer size() {
        return trips.size();
    }

    public void delete(Integer index) throws InvalidCommand {
        logger.log(Level.INFO, "Deleting trip");
        try {
            Ui.printDeleteTripMessage(trips.get(index - 1).abbrInfo());
            trips.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
        }
        logger.log(Level.INFO, "Finished deleting trip");
    }

    public void delete(String name) throws TripNotFoundException {
        logger.log(Level.INFO, "Deleting trip");
        Ui.printDeleteTripMessage(get(name).abbrInfo());
        trips.remove(get(name));
        logger.log(Level.INFO, "Finished deleting trip");
    }


    public Trip get(String name) throws TripNotFoundException {
        for (Trip trip : trips) {
            if (trip.getName().equals(name)) {
                return trip;
            }
        }
        throw new TripNotFoundException();
    }

    public Trip get(Integer index) throws InvalidIndex {
        try {
            return trips.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndex();
        }
    }

    @Override
    public String toString() {
        // early return for empty trip list
        if (trips.isEmpty()) {
            Ui.printEmptyTripList();
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < trips.size(); i++) {
            sb.append("Trip ").append(i + 1).append(": ").append(trips.get(i).abbrInfo()).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (!(obj instanceof TripList)) {
            return false;
        }
        for (int i = 0; i < trips.size(); i++) {
            if (!trips.get(i).equals(((TripList) obj).trips.get(i))) {
                return false;
            }
        }
        return true;
    }

    public void listTrip(Integer index) {
        logger.log(Level.INFO, "Listing trip");
        try {
            System.out.println(trips.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            Ui.printInvalidIndex();
        }
        logger.log(Level.INFO, "Finished listing trip");
    }

    // the following methods are for loading and saving the trip data

    // Serialize the trip list to JSON format
    public Optional<JSONObject> toJson() {
        // early return if trip list is empty
        if (trips.isEmpty()) {
            return Optional.empty();
        }

        JSONObject json = new JSONObject();
        JSONArray tripsArray = new JSONArray();
        for (Trip trip : trips) {
            tripsArray.put(trip.toJson());
        }
        json.put("trips", tripsArray);

        return Optional.of(json);
    }

    // Deserialize the trip list from JSON format
    public static TripList fromJson(String jsonStr) {
        JSONObject tripListJson = new JSONObject(jsonStr);

        // early return if trip list JSON is empty
        if (tripListJson.isEmpty()) {
            return new TripList();
        }

        TripList tripList = new TripList();
        JSONArray tripsArray = tripListJson.getJSONArray("trips");
        for (int i = 0; i < tripsArray.length(); i++) {
            JSONObject tripJson = tripsArray.getJSONObject(i);
            Trip trip = Trip.fromJson(tripJson);
            tripList.trips.add(trip);
        }
        return tripList;
    }


    // setters
    public void setTrips(ArrayList<Trip> trips) {
        this.trips = new ArrayList<>(trips);
    }
}
