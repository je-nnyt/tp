package voyatrip;

import java.util.Objects;
import java.util.Scanner;

import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.TripNotFoundException;
import voyatrip.command.types.AccommodationCommand;
import voyatrip.command.types.Command;
import voyatrip.command.types.CommandAction;
import voyatrip.command.types.ItineraryCommand;
import voyatrip.command.types.TransportationCommand;
import voyatrip.command.types.TripsCommand;
import voyatrip.command.Parser;
import voyatrip.ui.Ui;

import static voyatrip.command.types.CommandAction.EXIT;

/**
 * This is the main class for the VoyaTrip application.
 */
public class VoyaTrip {
    private static final Parser parser = new Parser();
    private static final Scanner in = new Scanner(System.in);
    private static TripList trips = new TripList();
    private static Boolean isExit = false;

    public static void main(String[] args) {
        run();
    }

    /**
     * This is the main loop for the VoyaTrip application.
     * It will keep running until the user exits the application
     */
    private static void run() {
        Ui.printWelcomeMessage();
        while (!isExit) {
            handleInput(readInput());
        }
        Ui.printGoodbyeMessage();
    }


    private static String readInput() {
        return in.nextLine();
    }

    private static void handleInput(String input) {
        try {
            Command command = parser.parse(input);
            handleCommand(command);
        } catch (TripNotFoundException e) {
            Ui.printTripNotFound();
        } catch (InvalidCommand e) {
            Ui.printInvalidCommand();
        }
    }

    private static void handleCommand(Command command) throws InvalidCommand, TripNotFoundException {
        if (EXIT.equals(command.getCommandAction())) {
            handleExit();
            return;
        }

        switch (command.getCommandTarget()) {
        case TRIP -> handleTrip((TripsCommand) command);
        case ITINERARY -> handleItinerary((ItineraryCommand) command);
        case ACTIVITY -> handleActivity((ItineraryCommand) command);
        case ACCOMMODATION -> handleAccommodation((AccommodationCommand) command);
        case TRANSPORTATION -> handleTransportation((TransportationCommand) command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleTrip(TripsCommand command) throws InvalidCommand{
        switch (command.getCommandAction()) {
        case ADD -> executeAddTrip(command);
        case DELETE -> executeDeleteTrip(command);
        case LIST -> executeListTrip(command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleItinerary(ItineraryCommand command) throws InvalidCommand {
        if (Objects.requireNonNull(command.getCommandAction()) == CommandAction.LIST) {
            executeListItinerary(command);
        } else {
            throw new InvalidCommand();
        }
    }

    private static void handleActivity(ItineraryCommand command) throws InvalidCommand, TripNotFoundException {
        switch (command.getCommandAction()) {
        case ADD -> executeAddActivity(command);
        case DELETE -> executeDeleteActivity(command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleAccommodation(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        switch (command.getCommandAction()) {
        case ADD -> executeAddAccommodation(command);
        case DELETE -> executeDeleteAccommodation(command);
        case LIST -> executeListAccommodation(command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleTransportation(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        switch (command.getCommandAction()) {
        case ADD -> executeAddTransportation(command);
        case DELETE -> executeDeleteTransportation(command);
        case LIST -> executeListTransportation(command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleExit() {
        isExit = true;
    }

    private static void executeAddTrip(TripsCommand command) throws InvalidCommand {
        trips.add(command.getName(),
                command.getStartDate(),
                command.getEndDate(),
                command.getNumDay(),
                command.getTotalBudget());
    }

    private static void executeAddActivity(Command command) {
    }

    private static void executeAddAccommodation(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getName()).addAccommodation(command.getName(), command.getBudget());
    }

    private static void executeAddTransportation(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getTrip()).addTransportation(command.getName(), command.getMode(), command.getBudget());
    }

    private static void executeDeleteTrip(TripsCommand command) throws InvalidCommand {
        trips.delete(command.getIndex());
    }

    private static void executeDeleteActivity(Command command) {
    }

    private static void executeDeleteAccommodation(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getTrip()).deleteAccommodation(command.getIndex());
    }

    private static void executeDeleteTransportation(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getName()).deleteTransportation(command.getIndex());
    }

    private static void executeListTrip(Command command) {
    }

    private static void executeListItinerary(Command command) {
    }

    private static void executeListAccommodation(Command command) {
    }

    private static void executeListTransportation(Command command) {
    }
}
