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
     *
     * @param startDate   the start date of the trip.
     * @param endDate     the end date of the trip.
     * @param numDays     the number of days for the trip.
     * @param totalBudget the total budget for the trip.
     */
    public Trip(String name, LocalDate startDate, LocalDate endDate, Integer numDays, Integer totalBudget) {
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.totalBudget = totalBudget;
        this.transportations = new ArrayList<>();
        this.accommodations = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public void addTransportation(String transportName,
                                  String transportMode,
                                  Integer transportBudget) throws InvalidCommand {
        Integer initialSize = transportations.size();
        if (isContainsTransportation(transportMode)) {
            throw new InvalidCommand();
        }
        Transportation newTransportation = new Transportation(transportName, transportMode, transportBudget);
        transportations.add(newTransportation);

        assert initialSize == transportations.size() + 1 : "Transportation list size did not increase";
        assert transportBudget >= 0 : "Budget must be a positive value";

        Ui.printAddTransportationMessage(newTransportation);
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
        Integer initialSize = transportations.size();
        try {
            Ui.printDeleteTransportationMessage(transportations.get(index - 1));
            transportations.remove(index - 1);

            assert initialSize == transportations.size() - 1 : "Transportation list size did not decrease";

        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommand();
        }
    }

    public void deleteTransportation(String transportName) throws InvalidCommand {
        for (Transportation transportation : transportations) {
            if (transportation.getName().equals(transportName)) {
                Ui.printDeleteTransportationMessage(transportation);
                transportations.remove(transportation);
                return;
            }
        }
        throw new InvalidCommand();
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

    public void deleteAccommodation(String accommodationName) throws InvalidCommand {
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getName().equals(accommodationName)) {
                accommodations.remove(accommodation);
                Ui.printDeleteAccommodationMessage(accommodation);
                return;
            }
        }
        throw new InvalidCommand();
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

    private void buildAccommodationsInfo(StringBuilder tripInfo) {
        for (Accommodation accommodation : accommodations) {
            tripInfo.append(accommodation.toString()).append("\n");

            assert accommodation.toString() != null : "Accommodation information should not be null";
            assert accommodation.toString().length() > 0 : "Accommodation information should not be empty";
        }
    }

    private void buildTransportationsInfo(StringBuilder tripInfo) {
        for (Transportation transportation : transportations) {
            tripInfo.append(transportation.toString()).append("\n");

            assert transportation.toString() != null : "Transportation information should not be null";
            assert transportation.toString().length() > 0 : "Transportation information should not be empty";
        }
    }

    /**
     * This is a method to print the trip information.
     *
     * @return String representation of the trip, and its associated transportations and accommodations.
     */
    @Override
    public String toString() {
        StringBuilder tripInfo = new StringBuilder();
        tripInfo.append(abbrInfo()).append("\n");

        tripInfo.append("Transportations:\n");
        buildTransportationsInfo(tripInfo);
        tripInfo.append("Accommodations:\n");
        buildAccommodationsInfo(tripInfo);
        return tripInfo.toString().trim();
    }
}

