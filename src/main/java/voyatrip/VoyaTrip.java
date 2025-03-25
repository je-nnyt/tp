package voyatrip;

import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.TripNotFoundException;
import voyatrip.command.types.AccommodationCommand;
import voyatrip.command.types.Command;
import voyatrip.command.types.CommandAction;
import voyatrip.command.types.CommandTarget;
import voyatrip.command.types.ItineraryCommand;
import voyatrip.command.types.TransportationCommand;
import voyatrip.command.types.TripsCommand;
import voyatrip.command.Parser;
import voyatrip.ui.Ui;

/**
 * This is the main class for the VoyaTrip application.
 */
public class VoyaTrip {
    static final Parser parser = new Parser();
    static final Scanner in = new Scanner(System.in);
    static TripList trips = new TripList();
    static Boolean isExit = false;
    private static final Logger logger = Logger.getLogger(VoyaTrip.class.getName());

    public static void main(String[] args) {
        run();
    }

    /**
     * This is the main loop for the VoyaTrip application.
     * It will keep running until the user exits the application
     */
    private static void run() {
        logger.log(Level.INFO, "Starting VoyaTrip application");
        Ui.printWelcomeMessage();
        while (!isExit) {
            Ui.printCurrentPath(parser);
            handleInput(readInput());
        }
        Ui.printGoodbyeMessage();
        logger.log(Level.INFO, "Exiting VoyaTrip application");
    }


    private static String readInput() {
        return in.nextLine();
    }

    private static void handleInput(String input) {
        try {
            logger.log(Level.INFO, "Starting handleInput");
            Command command = parser.parse(input);
            handleCommand(command);
            logger.log(Level.INFO, "Finished handleInput");
        } catch (TripNotFoundException e) {
            logger.log(Level.WARNING, "Trip not found");
            Ui.printTripNotFound();
        } catch (InvalidCommand e) {
            logger.log(Level.WARNING, "Invalid command");
            Ui.printInvalidCommand();
        }
    }

    private static void handleCommand(Command command) throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting handleCommand");
        if (CommandAction.EXIT.equals(command.getCommandAction())) {
            handleExit();
            logger.log(Level.INFO, "Exiting application");
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

    private static void handleTrip(TripsCommand command) throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting handleTrip");
        switch (command.getCommandAction()) {
        case ADD -> executeAddTrip(command);
        case DELETE_BY_INDEX -> executeDeleteTripByIndex(command);
        case DELETE_BY_NAME -> executeDeleteTripByName(command);
        case LIST -> executeListTrip(command);
        case CHANGE_TRIP_BY_NAME -> executeChangeDirectoryTripByName(command);
        case CHANGE_TRIP_BY_INDEX -> executeChangeDirectoryTripByIndex(command);
        default -> throw new InvalidCommand();
        }
        logger.log(Level.INFO, "Finished handleTrip");
    }

    private static void handleItinerary(ItineraryCommand command) throws InvalidCommand {
        switch (command.getCommandAction()) {
        case LIST -> executeListItinerary(command);
        case CHANGE_DIRECTORY -> executeChangeDirectoryItinerary(command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleActivity(ItineraryCommand command) throws InvalidCommand, TripNotFoundException {
        switch (command.getCommandAction()) {
        case ADD -> executeAddActivity(command);
        case DELETE_BY_INDEX -> executeDeleteActivity(command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleAccommodation(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        switch (command.getCommandAction()) {
        case ADD -> executeAddAccommodation(command);
        case DELETE_BY_INDEX -> executeDeleteAccommodationByIndex(command);
        case DELETE_BY_NAME -> executeDeleteAccommodationByName(command);
        case LIST -> executeListAccommodation(command);
        case CHANGE_DIRECTORY -> executeChangeDirectoryAccommodation(command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleTransportation(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        switch (command.getCommandAction()) {
        case ADD -> executeAddTransportation(command);
        case DELETE_BY_INDEX -> executeDeleteTransportationByIndex(command);
        case DELETE_BY_NAME -> executeDeleteTransportationByName(command);
        case LIST -> executeListTransportation(command);
        case CHANGE_DIRECTORY -> executeChangeDirectoryTransportation(command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleExit() {
        isExit = true;
    }

    private static void executeAddTrip(TripsCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeAddTrip");
        trips.add(command.getName(),
                command.getStartDate(),
                command.getEndDate(),
                command.getNumDay(),
                command.getTotalBudget());
        logger.log(Level.INFO, "Finished executeAddTrip");
    }

    private static void executeAddActivity(ItineraryCommand command) throws TripNotFoundException {
        trips.get(command.getTrip()).addActivity(command.getDay(), command.getName(), command.getTime());
    }

    private static void executeAddAccommodation(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getTrip()).addAccommodation(command.getName(), command.getBudget());
    }

    private static void executeAddTransportation(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getTrip()).addTransportation(command.getName(), command.getMode(), command.getBudget());
    }

    private static void executeDeleteTripByIndex(TripsCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeDeleteTripByIndex");
        trips.delete(command.getIndex());
        logger.log(Level.INFO, "Finished executeDeleteTripByIndex");
    }

    private static void executeDeleteTripByName(TripsCommand command) throws TripNotFoundException {
        logger.log(Level.INFO, "Starting executeDeleteTripByName");
        trips.delete(command.getName());
        logger.log(Level.INFO, "Finished executeDeleteTripByName");
    }

    private static void executeDeleteActivity(Command command) {
    }

    private static void executeDeleteAccommodationByIndex(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getTrip()).deleteAccommodation(command.getIndex());
    }

    private static void executeDeleteAccommodationByName(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getTrip()).deleteAccommodation(command.getName());
    }

    private static void executeDeleteTransportationByIndex(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getTrip()).deleteTransportation(command.getIndex());
    }

    private static void executeDeleteTransportationByName(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        trips.get(command.getTrip()).deleteTransportation(command.getName());
    }

    private static void executeListTrip(TripsCommand command) {
        trips.listTrip(command.getIndex());
    }

    private static void executeListItinerary(Command command) {
    }

    private static void executeListAccommodation(Command command) {
    }

    private static void executeListTransportation(Command command) {
    }

    private static void executeChangeDirectoryTripByName(TripsCommand command) throws TripNotFoundException {
        if (command.getName().equals("root")) {
            parser.setCurrentTrip("");
            parser.setCurrentTarget(CommandTarget.TRIP);
        } else if (trips.isContains(command.getName())) {
            parser.setCurrentTrip(command.getName());
            parser.setCurrentTarget(CommandTarget.ITINERARY);
        } else {
            throw new TripNotFoundException();
        }
    }

    private static void executeChangeDirectoryTripByIndex(TripsCommand command) throws InvalidCommand {
        parser.setCurrentTrip(trips.get(command.getIndex()).getName());
        parser.setCurrentTarget(CommandTarget.ITINERARY);
    }

    private static void executeChangeDirectoryItinerary(ItineraryCommand command) {
        parser.setCurrentTarget(CommandTarget.ITINERARY);
    }

    private static void executeChangeDirectoryAccommodation(AccommodationCommand command) {
        parser.setCurrentTarget(CommandTarget.ACCOMMODATION);
    }

    private static void executeChangeDirectoryTransportation(TransportationCommand command) {
        parser.setCurrentTarget(CommandTarget.TRANSPORTATION);
    }
}
