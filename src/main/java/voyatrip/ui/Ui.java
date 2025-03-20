package voyatrip.ui;

import voyatrip.Accommodation;
import voyatrip.command.Parser;
import voyatrip.Transportation;

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
