package voyatrip;

import java.util.ArrayList;

import java.time.LocalDate;

import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.ui.Ui;

/**
 * This is the trip class that will hold all the information about the trip.
 */
public class Trip {
    private final String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalBudget;
    private Integer numDays;
    private ArrayList<Transportation> transportations;
    private ArrayList<Accommodation> accommodations;

    /**
     * Constructor for the trip class.
     * @param startDate the start date of the trip.
     * @param endDate the end date of the trip.
     * @param numDays the number of days for the trip.
     * @param totalBudget the total budget for the trip.
     */
    public Trip(String name, LocalDate startDate, LocalDate endDate, Integer numDays, Integer totalBudget) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.totalBudget = totalBudget;
        this.accommodations = new ArrayList<>();
        this.transportations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public ArrayList<Transportation> getTransportations() {
        return transportations;
    }

    public void addTransportation(String transportName,
                                  String transportMode,
                                  Integer transportBudget) throws InvalidCommand {
        if (isContainsTransportation(transportMode)) {
            throw new InvalidCommand();
        }
        transportations.add(new Transportation(transportName, transportMode, transportBudget));
        Ui.printAddTransportationMessage();
    }

    private boolean isContainsTransportation(String transportName) {
        for (Transportation transportation : transportations) {
            if (transportation.getName().equals(transportName)) {
                return true;
            }
        }
        return false;
    }

    public void deleteTransportation(Integer index) throws InvalidCommand {
        try {
            transportations.remove(index - 1);
            Ui.printDeleteTransportationMessage();
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommand();
        }
    }

    public void addAccommodation(String accommodationName, Integer accommodationBudget) throws InvalidCommand {
        if (isContainsAccommodation(accommodationName)) {
            throw new InvalidCommand();
        }
        Accommodation newAccommodation = new Accommodation(accommodationName, accommodationBudget);
        accommodations.add(newAccommodation);
        Ui.printAddAccommodationMessage(newAccommodation);
    }

    private boolean isContainsAccommodation(String accommodationName) {
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getName().equals(accommodationName)) {
                return true;
            }
        }
        return false;
    }

    public void deleteAccommodation(Integer index) throws InvalidCommand {
        try {
            Ui.printDeleteAccommodationMessage(accommodations.get(index - 1));
            accommodations.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommand();
        }
    }

    public String abbrInfo() {
        return name + ": " + startDate + "->" + endDate + " (days: " + numDays + ", budget: " + totalBudget + ")";
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != this.getClass()) {
            return false;
        }

        return this.name.equals(((Trip) obj).name);
    }
}

