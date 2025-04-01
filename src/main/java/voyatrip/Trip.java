package voyatrip;

import java.util.ArrayList;

import java.time.LocalDate;

import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.TransportationException;
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
        this.itinerary = new ArrayList<>();

        Integer budgetPerDay = totalBudget / numDays;
        for (int i = 0; i < numDays; i++) {
            itinerary.add(new Day(budgetPerDay));
        }
    }

    public String getName() {
        return name;
    }

    public void addTransportation(String transportName,
                                  String transportMode,
                                  Integer transportBudget) throws InvalidCommand {
        if (isContainsTransportation(transportMode)) {
            throw new InvalidCommand();
        }
        Transportation newTransportation = new Transportation(transportName, transportMode, transportBudget);
        transportations.add(newTransportation);
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
        try {
            Ui.printDeleteTransportationMessage(transportations.get(index - 1));
            transportations.remove(index - 1);
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

    public void listTransportation(Integer index) throws TransportationException {
        try {
            if (index < 0 || index > transportations.size()) {
                throw new TransportationException("Invalid index");
            }
            transportations.get(index).toString();
        } catch (TransportationException e) {
            System.out.println(e.getMessage());
        }
    }

    public void listTransportations() throws TransportationException {
        try {
            if (transportations.isEmpty()) {
                throw new TransportationException("There are no transportations in your current trip");
            }
            for (Transportation transportation : transportations) {
                transportation.toString();
            }
        } catch (TransportationException e) {
            System.out.println(e.getMessage());
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

    public void addActivity(Integer day, String name, String time) {
        try {
            Activity newActivity = new Activity(name, time);
            itinerary.get(day - 1).addActivity(newActivity);
            Ui.printAddActivityMessage(newActivity);
        } catch (IndexOutOfBoundsException e) {
            Ui.printIndexOutOfBounds();
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
        for (Day day : itinerary) {
            tripInfo.append(day.toString()).append("\n");
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

        tripInfo.append("Itinerary:\n");
        buildItineraryInfo(tripInfo);
        tripInfo.append("Transportations:\n");
        buildTransportationsInfo(tripInfo);
        tripInfo.append("Accommodations:\n");
        buildAccommodationsInfo(tripInfo);
        return tripInfo.toString().trim();
    }
}

