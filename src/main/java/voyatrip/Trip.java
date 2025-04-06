package voyatrip;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;
import voyatrip.command.exceptions.InvalidArgumentValue;
import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.InvalidIndex;
import voyatrip.ui.Ui;

/**
 * This is the trip class that will hold all the information about the trip.
 */
public class Trip {
    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer totalBudget;
    private Integer numDays;
    private ArrayList<Transportation> transportations;
    private ArrayList<Accommodation> accommodations;
    private ArrayList<Day> itineraries;
    private Logger logger = Logger.getLogger(Trip.class.getName());

    /**
     * Constructor for the trip class.
     *
     * @param startDate   the start date of the trip.
     * @param endDate     the end date of the trip.
     * @param numDays     the number of days for the trip.
     * @param totalBudget the total budget for the trip.
     */
    public Trip(String name, LocalDate startDate, LocalDate endDate, Integer numDays, Integer totalBudget) {
        assert (startDate.isBefore(endDate));
        assert (numDays == ChronoUnit.DAYS.between(startDate, endDate) + 1);
        logger.log(Level.INFO, "Creating new trip");
        this.name = name;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numDays = numDays;
        this.totalBudget = totalBudget;
        this.transportations = new ArrayList<>();
        this.accommodations = new ArrayList<>();
        this.itineraries = new ArrayList<>();

        Float budgetPerDay = (float) totalBudget / numDays;
        for (int i = 0; i < numDays; i++) {
            itineraries.add(new Day(budgetPerDay));
        }
        logger.log(Level.INFO, "Finished creating new trip");
    }

    public String getName() {
        return name;
    }

    public void addTransportation(String transportName,
                                  String transportMode,
                                  Integer transportBudget,
                                  Integer startDay,
                                  Integer endDay) throws InvalidCommand {
        logger.log(Level.INFO, "Adding transportation");
        if (isContainsTransportation(transportName)) {
            logger.log(Level.WARNING, "Transportation already exists");
            throw new InvalidArgumentValue();
        }
        Transportation newTransportation = new Transportation(transportName, transportMode,
                transportBudget, startDay, endDay);
        transportations.add(newTransportation);
        Ui.printAddTransportationMessage(newTransportation);
        printBudgetStatus();
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
            printBudgetStatus();
            logger.log(Level.INFO, "Finished deleting transportation");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
        }
    }

    public void deleteTransportation(String transportName) throws InvalidCommand {
        logger.log(Level.INFO, "Deleting transportation");
        for (Transportation transportation : transportations) {
            if (transportation.getName().equals(transportName)) {
                Ui.printDeleteTransportationMessage(transportation);
                transportations.remove(transportation);
                printBudgetStatus();
                logger.log(Level.INFO, "Finished deleting transportation");
                return;
            }
        }
        logger.log(Level.WARNING, "Transportation not found");
        throw new InvalidArgumentValue();
    }

    /**
     * This method prints the information of the transportation at the given index.
     *
     * @param index Index input by user
     * @throws InvalidIndex if invalid index
     */
    public void listTransportation(Integer index) throws InvalidCommand {
        logger.log(Level.INFO, "Listing transportation");
        try {
            Ui.printListTransportationMessage(transportations.get(index - 1));
            logger.log(Level.INFO, "Finished listing transportation");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "IndexOutOfBoundsException Exception");
            throw new InvalidIndex();
        }
    }

    public void listTransportation(String name) throws InvalidCommand {
        logger.log(Level.INFO, "Listing transportation");
        for (Transportation transportation : transportations) {
            if (transportation.getName().equals(name)) {
                Ui.printListTransportationMessage(transportation);
                logger.log(Level.INFO, "Finished listing transportation");
                return;
            }
        }
        logger.log(Level.WARNING, "Transportation not found");
        throw new InvalidArgumentValue();
    }

    public void addAccommodation(String accommodationName, Integer accommodationBudget,
                                 ArrayList<Integer> accommodationDays) throws InvalidCommand {
        logger.log(Level.INFO, "Adding accommodation");
        if (isContainsAccommodation(accommodationName)) {
            logger.log(Level.WARNING, "Accommodation already exists");
            throw new InvalidArgumentValue();
        }
        Accommodation newAccommodation = new Accommodation(accommodationName, accommodationBudget, accommodationDays);
        accommodations.add(newAccommodation);
        Ui.printAddAccommodationMessage(newAccommodation);
        printBudgetStatus();
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
            printBudgetStatus();
            logger.log(Level.INFO, "Finished deleting accommodation");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
        }
    }

    public void deleteAccommodation(String accommodationName) throws InvalidCommand {
        logger.log(Level.INFO, "Deleting accommodation");
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getName().equals(accommodationName)) {
                Ui.printDeleteAccommodationMessage(accommodation);
                accommodations.remove(accommodation);
                printBudgetStatus();
                logger.log(Level.INFO, "Finished deleting accommodation");
                return;
            }
        }
        logger.log(Level.WARNING, "Accommodation not found");
        throw new InvalidArgumentValue();
    }

    public void modifyAccommodation(String accommodationName, Integer accommodationBudget,
                                    ArrayList<Integer> accommodationDays, Integer index) throws InvalidCommand {
        try {
            boolean budgetIsModified = false;
            if (accommodationName != null) {
                logger.log(Level.INFO, "Modifying accommodation name");
                accommodations.get(index - 1).setName(accommodationName);
                logger.log(Level.INFO, "Finished modifying accommodation name");
            }

            if (accommodationBudget != null) {
                logger.log(Level.INFO, "Modifying accommodation budget");
                accommodations.get(index - 1).setBudget(accommodationBudget);
                budgetIsModified = true;
                logger.log(Level.INFO, "Finished modifying accommodation budget");
            }

            if (accommodationDays != null) {
                logger.log(Level.INFO, "Modifying accommodation days");
                accommodations.get(index - 1).setDays(accommodationDays);
                logger.log(Level.INFO, "Finished modifying accommodation days");
            }

            Ui.printModifyAccommodationMessage(accommodations.get(index - 1));
            if (budgetIsModified) {
                printBudgetStatus();
            }
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
        }
    }

    public void listAccommodation(Integer index) throws InvalidCommand {
        try {
            logger.log(Level.INFO, "Listing accommodation");
            Ui.printListAccommodationMessage(accommodations.get(index - 1));
            logger.log(Level.INFO, "Finished listing accommodation");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
        }
    }

    public void listAccommodation(String accommodationName) throws InvalidCommand {
        logger.log(Level.INFO, "Listing accommodation");
        for (Accommodation accommodation : accommodations) {
            if (accommodation.getName().equals(accommodationName)) {
                Ui.printListAccommodationMessage(accommodation);
                logger.log(Level.INFO, "Finished listing accommodation");
                return;
            }
        }
        logger.log(Level.WARNING, "Accommodation not found");
        throw new InvalidArgumentValue();
    }

    public void addActivity(Integer day, String name, String time) throws InvalidCommand {
        logger.log(Level.INFO, "Adding activity");
        try {
            Activity newActivity = new Activity(name, time);
            itineraries.get(day - 1).addActivity(newActivity);
            Ui.printAddActivityMessage(newActivity);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
        }
        logger.log(Level.INFO, "Finished adding activity");
    }

    public void deleteActivity(Integer day, Integer index) throws InvalidCommand {
        logger.log(Level.INFO, "Deleting activity");
        try {
            itineraries.get(day - 1).deleteActivity(index);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
        }
    }

    public void deleteActivity(Integer day, String name) throws InvalidCommand {
        logger.log(Level.INFO, "Deleting activity");
        try {
            itineraries.get(day - 1).deleteActivity(name);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
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

        Trip trip = (Trip) obj;
        boolean transportationsEqual = true;
        for (int i = 0; i < transportations.size(); i++) {
            if (!(this.transportations.get(i).equals(trip.transportations.get(i)))) {
                transportationsEqual = false;
                break;
            }
        }

        boolean accommodationsEqual = true;
        for (int i = 0; i < accommodations.size(); i++) {
            if (!(this.accommodations.get(i).equals(trip.accommodations.get(i)))) {
                accommodationsEqual = false;
                break;
            }
        }

        boolean itinerariesEqual = true;
        for (int i = 0; i < itineraries.size(); i++) {
            if (!(this.itineraries.get(i).equals(trip.itineraries.get(i)))) {
                itinerariesEqual = false;
                break;
            }
        }

        return this.name.equals(trip.name)
                && this.startDate.equals(trip.startDate)
                && this.endDate.equals(trip.endDate)
                && this.totalBudget.equals(trip.totalBudget)
                && this.numDays.equals(trip.numDays)
                && transportationsEqual
                && accommodationsEqual
                && itinerariesEqual;
    }

    public void buildAccommodationsInfo(StringBuilder tripInfo) {
        // early return when there are no accommodations
        if (accommodations.isEmpty()) {
            tripInfo.append("No accommodations added yet.\n");
        }

        for (int i = 0; i < accommodations.size(); i++) {
            tripInfo.append(i + 1).append(". ").append(accommodations.get(i).toString()).append("\n");
        }
    }

    public void buildTransportationsInfo(StringBuilder tripInfo) {
        // early return when there are no transportations
        if (transportations.isEmpty()) {
            tripInfo.append("No transportations added yet.\n");
        }

        for (int i = 0; i < transportations.size(); i++) {
            tripInfo.append(i + 1).append(". ").append(transportations.get(i).toString()).append("\n");
        }
    }

    public void buildItineraryInfo(StringBuilder tripInfo) {
        for (int i = 0; i < itineraries.size(); i++) {
            tripInfo.append("Day ").append(i + 1).append("\n");
            tripInfo.append(itineraries.get(i)).append("\n");
        }
    }

    /**
     * This method print the information of the current trip budget status, ie budget per day and remaining budget.
     */
    public void printBudgetStatus() {
        float budgetSum = 0;
        for (Day day : itineraries) {
            budgetSum += day.getBudget();
        }

        for (Transportation transportation : transportations) {
            budgetSum += transportation.getBudget();
        }

        for (Accommodation accommodation : accommodations) {
            budgetSum += accommodation.getBudget();
        }

        Ui.printTotalBudgetStatus(totalBudget, budgetSum);
        if (budgetSum > totalBudget) {
            Ui.printExceedTotalBudget();
            Ui.printBudgetPerDay(itineraries);
            Ui.printBudgetPerTransportation(transportations);
            Ui.printBudgetPerAccommodation(accommodations);
        }
    }

    /**
     * This method is used to correct the size of the day objects according to the number of days in the trip.
     */
    public void updateItinerarySize() {
        assert (ChronoUnit.DAYS.between(startDate, endDate) + 1 >= 0);
        int curSize = itineraries.size();
        int curNumDays = (int) ChronoUnit.DAYS.between(startDate, endDate) + 1;

        if (curSize < curNumDays) {
            for (int i = curSize; i < curNumDays; i++) {
                itineraries.add(new Day((float) 0));
            }
        } else if (curSize > curNumDays) {
            for (int i = curSize; i > curNumDays; i--) {
                itineraries.remove(i - 1);
            }
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

        tripInfo.append("Itinerary: \n");
        buildItineraryInfo(tripInfo);

        tripInfo.append("Transportations:\n");
        buildTransportationsInfo(tripInfo);
        tripInfo.append("\n");

        tripInfo.append("Accommodations:\n");
        buildAccommodationsInfo(tripInfo);

        return tripInfo.toString().trim();
    }

    // the following methods are for loading and saving the trip data

    /**
     * This method is used to convert the trip object to a JSON object.
     *
     * @return JSON object representation of the trip.
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("startDate", startDate.toString());
        json.put("endDate", endDate.toString());
        json.put("numDays", numDays);
        json.put("totalBudget", totalBudget);

        for (Transportation transportation : transportations) {
            json.append("transportations", transportation.toJson());
        }

        for (Accommodation accommodation : accommodations) {
            json.append("accommodations", accommodation.toJson());
        }

        for (Day day : itineraries) {
            json.append("itineraries", day.toJson());
        }

        return json;
    }

    /**
     * This method is used to convert the JSON object to a trip object.
     *
     * @param jsonObject JSON object representation of the trip.
     */
    public static Trip fromJson(JSONObject jsonObject) {
        String name = jsonObject.getString("name");
        LocalDate startDate = LocalDate.parse(jsonObject.getString("startDate"));
        LocalDate endDate = LocalDate.parse(jsonObject.getString("endDate"));
        Integer numDays = jsonObject.getInt("numDays");
        Integer totalBudget = jsonObject.getInt("totalBudget");

        Trip trip = new Trip(name, startDate, endDate, numDays, totalBudget);

        if (jsonObject.has("transportations")) {
            ArrayList<Transportation> transportations = new ArrayList<>();
            for (Object obj : jsonObject.getJSONArray("transportations")) {
                transportations.add(Transportation.fromJson((JSONObject) obj));
            }
            trip.setTransportations(transportations);
        }

        if (jsonObject.has("accommodations")) {
            ArrayList<Accommodation> accommodations = new ArrayList<>();
            for (Object obj : jsonObject.getJSONArray("accommodations")) {
                accommodations.add(Accommodation.fromJson((JSONObject) obj));
            }
            trip.setAccommodations(accommodations);
        }

        if (jsonObject.has("itineraries")) {
            ArrayList<Day> itineraries = new ArrayList<>();
            for (Object obj : jsonObject.getJSONArray("itineraries")) {
                itineraries.add(Day.fromJson((JSONObject) obj));
            }
            trip.setItineraries(itineraries);
        }

        return trip;
    }

    // setters
    public void setName(String name) {
        assert (name != null);
        this.name = name;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public void setNumDays(Integer numDays) {
        this.numDays = numDays;
    }

    public void setTotalBudget(Integer totalBudget) {
        this.totalBudget = totalBudget;
    }

    public void setTransportations(ArrayList<Transportation> transportations) {
        this.transportations = transportations;
    }

    public void setAccommodations(ArrayList<Accommodation> accommodations) {
        this.accommodations = accommodations;
    }

    public void setItineraries(ArrayList<Day> itineraries) {
        this.itineraries = itineraries;
    }

    // getters
    public LocalDate getStartDate() {
        return startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getItinerarySize() {
        return itineraries.size();
    }
}

