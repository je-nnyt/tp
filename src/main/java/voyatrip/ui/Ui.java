package voyatrip.ui;

import voyatrip.Accommodation;
import voyatrip.Activity;
import voyatrip.Day;
import voyatrip.command.Parser;
import voyatrip.Transportation;

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

    // Methods for trip modification
    public static void printBudgetPerDay(ArrayList<Day> itinerary) {
        System.out.println(Message.BUDGET_PER_DAY_MESSAGE);
        for (int i = 0; i < itinerary.size(); i++) {
            System.out.println("Day " + (i + 1) + ": $" + itinerary.get(i).getBudget());
        }
    }

    public static void printExceedTotalBudget() {
        System.out.println(Message.EXCEED_TOTAL_BUDGET_MESSAGE);
    }

    public static void printTotalBudgetStatus(Integer totalBudget, Integer budgetSum) {
        System.out.println(Message.TOTAL_BUDGET_AGAINST_BUDGET_SUM_MESSAGE
                + (budgetSum / totalBudget) * 100 + "%");
    }

    public static void printInvalidModificationOfDate() {
        System.out.println(Message.INVALID_MODIFICATION_DATES_MESSAGE);
    }

    public static void printModifyTripMessage(String abbrTripInfo) {
        System.out.println(Message.MODIFY_TRIP_MESSAGE);
        System.out.println(abbrTripInfo);
        System.out.println(Message.NEXT_COMMAND_MESSAGE);
    }

    public static void printTripNotFound() {
        System.out.println(Message.TRIP_NOT_FOUND_MESSAGE);
    }

    public static void printInvalidCommand() {
        System.out.println(Message.INVALID_COMMAND_MESSAGE);
    }

    public static void printIndexOutOfBounds() {
        System.out.println(Message.INDEX_OUT_OF_BOUNDS_MESSAGE);
    }
}
