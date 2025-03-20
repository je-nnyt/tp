package voyatrip;

import java.time.LocalDate;
import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.TripNotFoundException;
import voyatrip.ui.Ui;

public class TripList {
    private static ArrayList<Trip> trips = new ArrayList<>();

    public void add(String name,
                    LocalDate startDate,
                    LocalDate endDate,
                    Integer numDays,
                    Integer totalBudget) throws InvalidCommand {
        if (isContains(name)) {
            throw new InvalidCommand();
        }

        Trip newTrip = new Trip(name, startDate, endDate, numDays, totalBudget);
        trips.add(newTrip);
        Ui.printAddTripMessage(newTrip.abbrInfo());
    }

    public boolean isContains(String name) {
        for (Trip trip : trips) {
            if (trip.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    public void delete(Integer index) throws InvalidCommand {
        try {
            Ui.printDeleteTripMessage(trips.get(index - 1).abbrInfo());
            trips.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommand();
        }
    }

    public void delete(String name) throws TripNotFoundException {
        Ui.printDeleteTripMessage(get(name).abbrInfo());
        trips.remove(get(name));
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
        try {
            System.out.println(trips.get(index - 1));
        } catch (IndexOutOfBoundsException e) {
            Ui.printIndexOutOfBounds();
        }
    }
}
