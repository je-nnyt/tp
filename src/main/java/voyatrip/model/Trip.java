package voyatrip.model;

import java.time.temporal.ChronoUnit;
import java.util.ArrayList;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.Objects;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.json.JSONObject;

import voyatrip.logic.command.exceptions.AccommodationNotFound;
import voyatrip.logic.command.exceptions.DuplicatedName;
import voyatrip.logic.command.exceptions.InvalidArgumentValue;
import voyatrip.logic.command.exceptions.InvalidCommand;
import voyatrip.logic.command.exceptions.InvalidDuplicateActivity;
import voyatrip.logic.command.exceptions.InvalidDay;
import voyatrip.logic.command.exceptions.InvalidIndex;
import voyatrip.logic.command.exceptions.TransportationNotFound;
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
                                  Integer day) throws InvalidCommand {
        logger.log(Level.INFO, "Adding transportation");

        if (isContainsTransportation(transportName)) {
            logger.log(Level.WARNING, "Transportation already exists");
            throw new DuplicatedName();
        }
        validateTransportationDay(day);

        Transportation newTransportation = new Transportation(transportName, transportMode, transportBudget, day);
        transportations.add(newTransportation);
        Ui.printAddTransportationMessage(newTransportation);
        printBudgetStatus();
        logger.log(Level.INFO, "Finished adding transportation");
    }

    private void validateTransportationDay(Integer day) throws InvalidArgumentValue {
        if (day < 1 || day > numDays) {
            throw new InvalidDay();
        }
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
        throw new TransportationNotFound();
    }

    /**
     * This method prints the information of the transportation at the given index.
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
        throw new TransportationNotFound();
    }

    public void modifyTransportation(String transportName, String transportMode, Integer transportBudget,
                                     Integer day, Integer index) throws InvalidArgumentValue, InvalidIndex {
        logger.log(Level.INFO, "Modifying transportation");

        try {
            if (transportName != null) {
                logger.log(Level.INFO, "Starting modifying name");
                transportations.get(index - 1).setName(transportName);
                logger.log(Level.INFO, "Finishing modifying name");
            }
            if (transportMode != null) {
                logger.log(Level.INFO, "Starting modifying mode");
                transportations.get(index - 1).setMode(transportMode);
                logger.log(Level.INFO, "Finishing modifying mode");
            }
            if (transportBudget != null) {
                logger.log(Level.INFO, "Starting modifying budget");
                transportations.get(index - 1).setBudget(transportBudget);
                logger.log(Level.INFO, "Finishing modifying budget");
            }
            if (day != null) {
                logger.log(Level.INFO, "Starting modifying day");
                validateTransportationDay(day);
                transportations.get(index - 1).setDay(day);
                logger.log(Level.INFO, "Finishing modifying day");
            }
            Ui.printModifyTransportationMessage(transportations.get(index));
            logger.log(Level.INFO, "Finishing modifying transportation");
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidIndex();
        }
    }

    public void addAccommodation(String accommodationName, Integer accommodationBudget,
                                 ArrayList<Integer> accommodationDays) throws InvalidCommand {
        logger.log(Level.INFO, "Adding accommodation");

        validateAccommodationName(accommodationName);

        ArrayList<Integer> dummyAccommodationDays = new ArrayList<>();
        validateAccommodationDays(accommodationDays, dummyAccommodationDays);

        Accommodation newAccommodation = new Accommodation(accommodationName, accommodationBudget, accommodationDays);

        accommodations.add(newAccommodation);
        Ui.printAddAccommodationMessage(newAccommodation);
        sortAccommodationsByDay();
        printBudgetStatus();
        logger.log(Level.INFO, "Finished adding accommodation");
    }

    private void validateAccommodationName(String accommodationName) throws DuplicatedName {
        if (isContainsAccommodation(accommodationName)) {
            logger.log(Level.WARNING, "Accommodation already exists");
            throw new DuplicatedName();
        }
    }

    private void validateAccommodationDays(ArrayList<Integer> newAccommodationDays,
                                           ArrayList<Integer> oldAccommodationDays) throws InvalidArgumentValue {
        if (newAccommodationDays.size() == 1) {
            logger.log(Level.WARNING, "Check-in and check-out are on the same day");
            throw new InvalidDay();
        }

        if (newAccommodationDays.get(0) < 1 || newAccommodationDays.get(newAccommodationDays.size() - 1) > numDays) {
            logger.log(Level.WARNING, "Accommodation days out of range");
            throw new InvalidDay();
        }

        if (hasAccommodationOverlap(newAccommodationDays, oldAccommodationDays)) {
            logger.log(Level.WARNING, "Accommodation overlap");
            throw new InvalidDay();
        }
    }

    private boolean hasAccommodationOverlap(ArrayList<Integer> newAccommodationDays,
                                            ArrayList<Integer> oldAccommodationDays) {
        for (Accommodation accommodation : accommodations) {
            if (!Objects.equals(oldAccommodationDays, accommodation.getDays())
                    && isDaysOverlap(newAccommodationDays, accommodation.getDays())) {
                return true;
            }
        }
        return false;
    }

    private boolean isDaysOverlap(ArrayList<Integer> days1, ArrayList<Integer> days2) {
        boolean isDay1BeforeDay2 = days1.get(0) < days2.get(0);
        boolean isDay2BeforeDay1 = days2.get(0) < days1.get(0);
        boolean isDay1Day2StartSame = Objects.equals(days1.get(0), days2.get(0));
        boolean isDay1EndsBeforeDay2Begins = days1.get(days1.size() - 1) <= days2.get(0);
        boolean isDay2EndsBeforeDay1Begins = days2.get(days2.size() - 1) <= days1.get(0);

        return (isDay1BeforeDay2 && !isDay1EndsBeforeDay2Begins) ||
                (isDay2BeforeDay1 && !isDay2EndsBeforeDay1Begins) || isDay1Day2StartSame;
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
        if ("all".equals(accommodationName)) {
            // Condition to delete all accommodations
            accommodations.clear();
            Ui.printDeleteAllAccommodationsMessage();
            logger.log(Level.INFO, "All accommodations are deleted");
            return;
        }

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
        throw new AccommodationNotFound();
    }

    public void modifyAccommodation(String accommodationName, Integer accommodationBudget,
                                    ArrayList<Integer> accommodationDays, Integer index) throws InvalidCommand {
        try {
            boolean budgetIsModified = false;
            if (accommodationName != null) {
                logger.log(Level.INFO, "Modifying accommodation name");
                validateAccommodationName(accommodationName);
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
                ArrayList<Integer> oldAccommodationDays = accommodations.get(index - 1).getDays();
                validateAccommodationDays(accommodationDays, oldAccommodationDays);
                accommodations.get(index - 1).setDays(accommodationDays);
                logger.log(Level.INFO, "Finished modifying accommodation days");
            }

            Ui.printModifyAccommodationMessage(accommodations.get(index - 1));
            sortAccommodationsByDay();
            if (budgetIsModified) {
                printBudgetStatus();
            }
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
        }
    }

    /**
     * Sorts the accommodation list in ascending order of the first element
     * of the 'days' attribute of each Accommodation.
     */
    private void sortAccommodationsByDay() {
        accommodations.sort(Comparator.comparing(accommodation -> accommodation.getDays().get(0)));
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
        throw new AccommodationNotFound();
    }

    public void addActivity(Integer day, String name, String time) throws InvalidCommand {
        logger.log(Level.INFO, "Adding activity");

        //verify if duplicate activity
        validateDuplicateActivity(day, name, time);

        try {
            Activity newActivity = new Activity(name, time);
            itineraries.get(day - 1).addActivity(newActivity);
            Ui.printAddActivityMessage(newActivity);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidDay();
        }
        logger.log(Level.INFO, "Finished adding activity");
    }

    private void validateDuplicateActivity(Integer day, String name, String time) throws InvalidDuplicateActivity {
        for (Activity activity : itineraries.get(day - 1).getActivities()) {
            if (activity.getName().equals(name) && activity.getTime().equals(time)) {
                throw new InvalidDuplicateActivity();
            }
        }
    }


    public void deleteActivity(Integer day, Integer index) throws InvalidCommand {
        logger.log(Level.INFO, "Deleting activity");
        try {
            itineraries.get(day - 1).deleteActivity(index);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidDay();
        }
    }

    public void deleteActivity(Integer day, String name) throws InvalidCommand {
        logger.log(Level.INFO, "Deleting activity");
        try {
            itineraries.get(day - 1).deleteActivity(name);
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidDay();
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
        final double EPSILON = 1e-2;
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
        if (budgetSum - totalBudget > EPSILON) {
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

    public Integer getNumDays() {
        return numDays;
    }

    public Integer getTotalBudget() {
        return totalBudget;
    }

    public Integer getItinerarySize() {
        return itineraries.size();
    }

    public ArrayList<Transportation> getTransportations() {
        return transportations;
    }
}

