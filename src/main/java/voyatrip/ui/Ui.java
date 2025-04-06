package voyatrip.ui;

import voyatrip.Accommodation;
import voyatrip.Activity;
import voyatrip.Day;
import voyatrip.command.Parser;
import voyatrip.Transportation;
import voyatrip.Trip;

import java.util.ArrayList;

/**
 * This class contains all the methods to access the user interface messages that will be displayed to the user.
 */
public class Ui {
    public static void printWelcomeMessage() {
        System.out.println(Message.WELCOME_MESSAGE);
    }

    public static void printGoodbyeMessage() {
        System.out.println(Message.GOODBYE_MESSAGE);
    }

    public static void printCurrentPath(Parser parser) {
        System.out.println();
        System.out.println(parser.getCurrentPath());
    }

    public static void printAddTripMessage(String abbrTripInfo) {
        System.out.println(Message.ADD_TRIP_MESSAGE);
        System.out.println(abbrTripInfo);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printDeleteTripMessage(String abbrTripInfo) {
        System.out.println(Message.DELETE_TRIP_MESSAGE);
        System.out.println(abbrTripInfo);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printAddAccommodationMessage(Accommodation accommodation) {
        System.out.println(Message.ADD_ACCOMMODATION_MESSAGE);
        System.out.println(accommodation);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printDeleteAccommodationMessage(Accommodation accommodation) {
        System.out.println(Message.DELETE_ACCOMMODATION_MESSAGE);
        System.out.println(accommodation);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printAddTransportationMessage(Transportation transportation) {
        System.out.println(Message.ADD_TRANSPORTATION_MESSAGE);
        System.out.println(transportation);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printDeleteTransportationMessage(Transportation transportation) {
        System.out.println(Message.DELETE_TRANSPORTATION_MESSAGE);
        System.out.println(transportation);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printAddActivityMessage(Activity activity) {
        System.out.println(Message.ADD_ACTIVITY_MESSAGE);
        System.out.println(activity);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printDeleteActivityMessage(Activity activity) {
        System.out.println(Message.DELETE_ACTIVITY_MESSAGE);
        System.out.println(activity);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printEmptyTripList() {
        System.out.println(Message.EMPTY_TRIP_LIST_MESSAGE);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printNextCommandMessage() {
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printItinerary(Trip trip) {
        StringBuilder tripInfo = new StringBuilder();
        trip.buildItineraryInfo(tripInfo);
        System.out.println(tripInfo.toString().trim());
    }

    // Methods for trip modification
    public static void printBudgetPerDay(ArrayList<Day> itineraries) {
        System.out.println(Message.BUDGET_PER_DAY_MESSAGE);
        for (int i = 0; i < itineraries.size(); i++) {
            System.out.println("Day " + (i + 1) + ": $" + itineraries.get(i).getBudget());
        }
    }

    public static void printSameTripNameMessage() {
        System.out.println(Message.SAME_TRIP_NAME_MESSAGE);
    }

    public static void printSameTripDatesMessage() {
        System.out.println(Message.SAME_TRIP_DATES_MESSAGE);
    }

    public static void printSameTripNumDaysMessage() {
        System.out.println(Message.SAME_TRIP_NUM_DAYS_MESSAGE);
    }

    public static void printSameTripBudgetMessage() {
        System.out.println(Message.SAME_TRIP_BUDGET_MESSAGE);
    }

    public static void printSameTripMessage() {
        System.out.println(Message.SAME_TRIP_MESSAGE);
    }

    public static void printBudgetPerTransportation(ArrayList<Transportation> transportations) {
        if (transportations.isEmpty()) {
            return;
        }
        System.out.println(Message.BUDGET_PER_TRANSPORTATION_MESSAGE);
        for (int i = 0; i < transportations.size(); i++) {
            System.out.println((i + 1) + ": $" + transportations.get(i).getBudget());
        }
    }

    public static void printBudgetPerAccommodation(ArrayList<Accommodation> accommodations) {
        if (accommodations.isEmpty()) {
            return;
        }
        System.out.println(Message.BUDGET_PER_ACCOMMODATION_MESSAGE);
        for (int i = 0; i < accommodations.size(); i++) {
            System.out.println((i + 1) + ": $" + accommodations.get(i).getBudget());
        }
    }

    public static void printExceedTotalBudget() {
        System.out.println(Message.EXCEED_TOTAL_BUDGET_MESSAGE);
    }

    public static void printTotalBudgetStatus(Integer totalBudget, Float budgetSum) {
        System.out.println(Message.TOTAL_BUDGET_AGAINST_BUDGET_SUM_MESSAGE
                + (budgetSum / totalBudget) * 100 + "%");
    }

    public static void printInvalidModificationOfDate() {
        System.out.println(Message.INVALID_MODIFICATION_DATES_MESSAGE);
    }

    public static void printModifyTripMessage(String abbrTripInfo) {
        System.out.println(Message.MODIFY_TRIP_MESSAGE);
        System.out.println(abbrTripInfo);
    }

    public static void printInvalidArgumentKeyword() {
        System.out.println(Message.INVALID_ARGUMENT_KEYWORD_MESSAGE);
    }

    public static void printInvalidArgumentValue() {
        System.out.println(Message.INVALID_ARGUMENT_VALUE_MESSAGE);
    }

    public static void printInvalidCommand() {
        System.out.println(Message.INVALID_COMMAND_MESSAGE);
    }

    public static void printInvalidCommandAction() {
        System.out.println(Message.INVALID_COMMAND_ACTION_MESSAGE);
    }

    public static void printInvalidCommandTarget() {
        System.out.println(Message.INVALID_COMMAND_TARGET_MESSAGE);
    }

    public static void printInvalidDateFormat() {
        System.out.println(Message.INVALID_DATE_FORMAT_MESSAGE);
    }

    public static void printInvalidNumberFormat() {
        System.out.println(Message.INVALID_NUMBER_FORMAT_MESSAGE);
    }

    public static void printInvalidScope() {
        System.out.println(Message.INVALID_SCOPE_MESSAGE);
    }

    public static void printMissingArgument() {
        System.out.println(Message.MISSING_ARGUMENT_MESSAGE);
    }

    public static void printMissingCommandKeyword() {
        System.out.println(Message.MISSING_COMMAND_KEYWORD_MESSAGE);
    }

    public static void printTripNotFound() {
        System.out.println(Message.TRIP_NOT_FOUND_MESSAGE);
    }

    public static void printInvalidIndex() {
        System.out.println(Message.INVALID_INDEX_MESSAGE);
    }

    public static void printListTransportationMessage(Transportation transportation) {
        System.out.println(Message.LIST_TRANSPORTATION_MESSAGE);
        System.out.println(transportation);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printTransportationList(Trip trip) {
        System.out.println(Message.LIST_ALL_TRANSPORTATIONS_MESSAGE);
        StringBuilder tripInfo = new StringBuilder();
        trip.buildTransportationsInfo(tripInfo);
        System.out.println(tripInfo.toString().trim());
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printModifyAccommodationMessage(Accommodation accommodation) {
        System.out.println(Message.MODIFY_ACCOMMODATION_MESSAGE);
        System.out.println(accommodation);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printListAccommodationMessage(Accommodation accommodation) {
        System.out.println(Message.LIST_ACCOMMODATION_MESSAGE);
        System.out.println(accommodation);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printAccommodationList(Trip trip) {
        System.out.println(Message.LIST_ALL_ACCOMMODATIONS_MESSAGE);
        StringBuilder tripInfo = new StringBuilder();
        trip.buildAccommodationsInfo(tripInfo);
        System.out.println(tripInfo.toString().trim());
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }
}
