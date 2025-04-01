package voyatrip;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

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
    private ArrayList<Day> itinerary;
    private Logger logger = Logger.getLogger(Trip.class.getName());

    /**
     * Constructor for the trip class.
     * @param startDate the start date of the trip.
     * @param endDate the end date of the trip.
     * @param numDays the number of days for the trip.
     * @param totalBudget the total budget for the trip.
     */
    public Trip(String name, LocalDate startDate, LocalDate endDate, Integer numDays, Integer totalBudget) {
        assert(startDate.isBefore(endDate));
        assert(numDays == ChronoUnit.DAYS.between(startDate, endDate)+1);
        logger.log(Level.INFO, "Creating new trip");
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.totalBudget = totalBudget;
        this.transportations = new ArrayList<>();
        this.accommodations = new ArrayList<>();
        this.itinerary = new ArrayList<>();

        Integer budgetPerDay = totalBudget / numDays;
        for (int i = 0; i < numDays; i++) {
            itinerary.add(new Day(budgetPerDay));
        }
        logger.log(Level.INFO, "Finished creating new trip");
    }

    public String getName() {
        return name;
    }

    public void addTransportation(String transportName,
                                  String transportMode,
                                  Integer transportBudget) throws InvalidCommand {
        logger.log(Level.INFO, "Adding transportation");
        if (isContainsTransportation(transportName)) {
            logger.log(Level.WARNING, "Transportation already exists");
            throw new InvalidCommand();
        }
        Transportation newTransportation = new Transportation(transportName, transportMode, transportBudget);
        transportations.add(newTransportation);
        Ui.printAddTransportationMessage(newTransportation);
        logger.log(Level.INFO, "Finished adding transportation");
    }

    public boolean isContainsTransportation(String transportName) {
        logger.log(Level.INFO, "Checking if transportation exists");
        for (Transportation transportation : transportations) {
            if (transportation.getName().equals(transportName)) {
                return true;
            }
        }
        return false;
    }

    public void deleteTransportation(Integer index) throws InvalidCommand {
        try {
            logger.log(Level.INFO, "Deleting transportation");
            Ui.printDeleteTransportationMessage(transportations.get(index - 1));
            transportations.remove(index - 1);
            logger.log(Level.INFO, "Finished deleting transportation");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidCommand();
        }
    }

    public void deleteTransportation(String transportName) throws InvalidCommand {
        logger.log(Level.INFO, "Deleting transportation");
        for (Transportation transportation : transportations) {
            if (transportation.getName().equals(transportName)) {
                Ui.printDeleteTransportationMessage(transportation);
                transportations.remove(transportation);
                logger.log(Level.INFO, "Finished deleting transportation");
                return;
            }
        }
        logger.log(Level.WARNING, "Transportation not found");
        throw new InvalidCommand();
    }

    public void addAccommodation(String accommodationName, Integer accommodationBudget,
                                 ArrayList<Integer> accommodationDays) throws InvalidCommand {
        logger.log(Level.INFO, "Adding accommodation");
        if (isContainsAccommodation(accommodationName)) {
            logger.log(Level.WARNING, "Accommodation already exists");
            throw new InvalidCommand();
        }
        Accommodation newAccommodation = new Accommodation(accommodationName, accommodationBudget, accommodationDays);
        accommodations.add(newAccommodation);
        Ui.printAddAccommodationMessage(newAccommodation);
        logger.log(Level.INFO, "Finished adding accommodation");
    }

    public boolean isContainsAccommodation(String accommodationName) {
        logger.log(Level.INFO, "Checking if accommodation exists");
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getName().equals(accommodationName)) {
                logger.log(Level.INFO, "Accommodation exists");
                return true;
            }
        }
        logger.log(Level.INFO, "Accommodation does not exist");
        return false;
    }

    public void deleteAccommodation(Integer index) throws InvalidCommand {
        try {
            logger.log(Level.INFO, "Deleting accommodation");
            Ui.printDeleteAccommodationMessage(accommodations.get(index - 1));
            accommodations.remove(index - 1);
            logger.log(Level.INFO, "Finished deleting accommodation");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidCommand();
        }
    }

    public void deleteAccommodation(String accommodationName) throws InvalidCommand {
        logger.log(Level.INFO, "Deleting accommodation");
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getName().equals(accommodationName)) {
                accommodations.remove(accommodation);
                Ui.printDeleteAccommodationMessage(accommodation);
                logger.log(Level.INFO, "Finished deleting accommodation");
                return;
            }
        }
        logger.log(Level.WARNING, "Accommodation not found");
        throw new InvalidCommand();
    }

    public void addActivity(Integer day, String name, String time) {
        logger.log(Level.INFO, "Adding activity");
        try {
            Activity newActivity = new Activity(name, time);
            itinerary.get(day - 1).addActivity(newActivity);
            Ui.printAddActivityMessage(newActivity);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            Ui.printIndexOutOfBounds();
        }
        logger.log(Level.INFO, "Finished adding activity");
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
        }
    }

    private void buildTransportationsInfo(StringBuilder tripInfo) {
        for (Transportation transportation : transportations) {
            tripInfo.append(transportation.toString()).append("\n");
        }
    }

    public void buildItineraryInfo(StringBuilder tripInfo) {
        for (Day day: itinerary) {
            tripInfo.append(day.toString()).append("\n");
        }
    }

    /**
     * This is a method to print the trip information.
     * @return String representation of the trip, and its associated transportations and accommodations.
     */
    @Override
    public String toString() {
        StringBuilder tripInfo = new StringBuilder();
        tripInfo.append(abbrInfo()).append("\n");

        tripInfo.append("Itinerary:\n");
        buildItineraryInfo(tripInfo);
        tripInfo.append("Transportations:\n");
        buildTransportationsInfo(tripInfo);
        tripInfo.append("Accommodations:\n");
        buildAccommodationsInfo(tripInfo);
        return tripInfo.toString().trim();
    }
}

