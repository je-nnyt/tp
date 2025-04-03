package voyatrip;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.InvalidIndex;
import voyatrip.command.exceptions.TripNotFoundException;
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
            throw new InvalidCommand();
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
            throw new InvalidCommand();
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
}
