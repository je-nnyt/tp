package voyatrip;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.TripNotFoundException;
import voyatrip.ui.Ui;

public class TripList {
    private static ArrayList<Trip> trips = new ArrayList<>();
    private static Logger logger = Logger.getLogger(TripList.class.getName());

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

    public Trip get(Integer index) throws InvalidCommand {
        try {
            return trips.get(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommand();
        }
    }

    public void listTrip(Integer index) {
        logger.log(Level.INFO, "Listing trip");
        try {
            System.out.println(trips.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            Ui.printIndexOutOfBounds();
        }
        logger.log(Level.INFO, "Finished listing trip");
    }
}
