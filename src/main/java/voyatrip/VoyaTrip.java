package voyatrip;

import static voyatrip.ui.Ui.printTransportationList;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.InvalidIndex;
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
    static final Parser PARSER = new Parser();
    static final Scanner IN = new Scanner(System.in);
    static TripList trips = new TripList();
    static Boolean isExit = false;
    private static final Logger logger = Logger.getLogger(voyatrip.VoyaTrip.class.getName());

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
            Ui.printCurrentPath(PARSER);
            handleInput(readInput());
        }
        Ui.printGoodbyeMessage();
        logger.log(Level.INFO, "Exiting VoyaTrip application");
    }


    private static String readInput() {
        return IN.nextLine();
    }

    private static void handleInput(String input) {
        try {
            logger.log(Level.INFO, "Starting handleInput");
            Command command = PARSER.parse(input);
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
        case LIST_TRIP_BY_INDEX -> executeListTripByIndex(command);
        case LIST_TRIP_BY_NAME -> executeListTripByName(command);
        case CHANGE_TRIP_BY_NAME -> executeChangeDirectoryTripByName(command);
        case CHANGE_TRIP_BY_INDEX -> executeChangeDirectoryTripByIndex(command);
        case MODIFY -> executeModifyTrip(command);
        case MODIFY_TRIP_WITHOUT_INDEX -> executeModifyCurTrip(command);
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
        case LIST_ACCOMMODATION_BY_INDEX -> executeListAccommodationByIndex(command);
        case LIST_ACCOMMODATION_BY_NAME -> executeListAccommodationByName(command);
        case CHANGE_DIRECTORY -> executeChangeDirectoryAccommodation(command);
        case MODIFY -> executeModifyAccommodation(command);
        default -> throw new InvalidCommand();
        }
    }

    private static void handleTransportation(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting handleTransportation");
        switch (command.getCommandAction()) {
        case ADD -> executeAddTransportation(command);
        case DELETE_BY_INDEX -> executeDeleteTransportationByIndex(command);
        case DELETE_BY_NAME -> executeDeleteTransportationByName(command);
        case LIST -> executeListTransportation(command);
        case CHANGE_DIRECTORY -> executeChangeDirectoryTransportation(command);
        default -> {
            logger.log(Level.WARNING, "Unknown command action: " + command.getCommandAction());
            throw new InvalidCommand();
        }
        }
        logger.log(Level.INFO, "Finished handleTransportation");
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
        logger.log(Level.INFO, "Starting executeAddAccommodation");
        trips.get(command.getTrip()).addAccommodation(command.getName(), command.getBudget(), command.getDays());
        logger.log(Level.INFO, "Finished executeAddAccommodation");
    }

    private static void executeAddTransportation(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting executeAddTransportation");
        trips.get(command.getTrip()).addTransportation(command.getName(), command.getMode(), command.getBudget(),
                command.getStartDay(), command.getEndDay());
        logger.log(Level.INFO, "Finished executeAddTransportation");
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
        logger.log(Level.INFO, "Starting executeDeleteAccommodationByIndex");
        trips.get(command.getTrip()).deleteAccommodation(command.getIndex());
        logger.log(Level.INFO, "Finished executeDeleteAccommodationByIndex");
    }

    private static void executeDeleteAccommodationByName(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting executeDeleteAccommodationByName");
        trips.get(command.getTrip()).deleteAccommodation(command.getName());
        logger.log(Level.INFO, "Finished executeDeleteAccommodationByName");
    }

    private static void executeDeleteTransportationByIndex(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting executeDeleteTransportationByIndex");
        trips.get(command.getTrip()).deleteTransportation(command.getIndex());
        logger.log(Level.INFO, "Finished executeDeleteTransportationByIndex");
    }

    private static void executeDeleteTransportationByName(TransportationCommand command)
            throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting executeDeleteTransportationByName");
        trips.get(command.getTrip()).deleteTransportation(command.getName());
        logger.log(Level.INFO, "Finished executeDeleteTransportationByName");
    }

    private static void executeListTripByIndex(TripsCommand command) throws InvalidCommand {
        System.out.println(trips.get(command.getIndex()));
    }

    private static void executeListTripByName(TripsCommand command) throws TripNotFoundException {
        if (command.getName().equals("all")) {
            System.out.println(trips);
        } else {
            System.out.println(trips.get(command.getName()));
        }
    }

    private static void executeListItinerary(Command command) {
    }

    private static void executeListAccommodationByIndex(AccommodationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeListAccommodationByIndex");
        trips.get(command.getTrip()).listAccommodation(command.getIndex());
        logger.log(Level.INFO, "Finished executeListAccommodationByIndex");
    }

    private static void executeListAccommodationByName(AccommodationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeListAccommodation");
        if (command.getName().equals("all")) {
            Ui.printAccommodationList(trips.get(command.getTrip()));
        } else {
            trips.get(command.getTrip()).listAccommodation(command.getName());
        }
        logger.log(Level.INFO, "Finished executeListAccommodation");
    }

    private static void executeListTransportation(TransportationCommand command) throws TripNotFoundException {
        logger.log(Level.INFO, "Starting executeListTransportation");

        if ("all".equals(String.valueOf(command.getName()))) {
            Ui.printTransportationListMessage();
            printTransportationList(trips.get(command.getTrip()));
        } else {
            trips.get(command.getTrip()).listTransportation(command.getIndex());
        }
        logger.log(Level.INFO, "Finished executeListTransportation");
    }

    private static void executeChangeDirectoryTripByName(TripsCommand command) throws TripNotFoundException {
        if (command.getName().equals("root")) {
            PARSER.setCurrentTrip("");
            PARSER.setCurrentTarget(CommandTarget.TRIP);
        } else if (trips.isContains(command.getName())) {
            PARSER.setCurrentTrip(command.getName());
            PARSER.setCurrentTarget(CommandTarget.ITINERARY);
        } else {
            throw new TripNotFoundException();
        }
    }

    private static void executeChangeDirectoryTripByIndex(TripsCommand command) throws InvalidCommand {
        PARSER.setCurrentTrip(trips.get(command.getIndex()).getName());
        PARSER.setCurrentTarget(CommandTarget.ITINERARY);
    }

    private static void executeChangeDirectoryItinerary(ItineraryCommand command) {
        PARSER.setCurrentTarget(CommandTarget.ITINERARY);
    }

    private static void executeChangeDirectoryAccommodation(AccommodationCommand command) {
        logger.log(Level.INFO, "Starting executeChangeDirectoryAccommodation");
        PARSER.setCurrentTarget(CommandTarget.ACCOMMODATION);
        logger.log(Level.INFO, "Finished executeChangeDirectoryAccommodation");
    }

    private static void executeChangeDirectoryTransportation(TransportationCommand command) {
        logger.log(Level.INFO, "Starting executeChangeDirectoryTransportation");
        PARSER.setCurrentTarget(CommandTarget.TRANSPORTATION);
        logger.log(Level.INFO, "Finished executeChangeDirectoryTransportation");
    }


    // The following methods are for modifying trips

    /*
     * Modify the trip with the given command
     * @param command The command to modify the trip
     * Note that if the size of the modified numsDay is different from the original,
     * the size of the itinerary will be added or removed at the end accordingly
     */
    private static void executeModifyTrip(TripsCommand command) {
        logger.log(Level.INFO, "Starting executeModifyTrip");
        try {
            Trip trip = trips.get(command.getIndex());

            assert (trip != null
                    && (command.getName() != null
                    || command.getStartDate() != null
                    || command.getEndDate() != null
                    || command.getTotalBudget() != null));

            if (command.getName() != null) {
                logger.log(Level.INFO, "Modifying trip name");
                trip.setName(command.getName());
            }

            // Checking for the validity of the date modification
            LocalDate newStartDate = (command.getStartDate() != null) ? command.getStartDate() : trip.getStartDate();
            LocalDate newEndDate = (command.getEndDate() != null) ? command.getEndDate() : trip.getEndDate();
            Integer newNumDays = command.getNumDay();
            if (newNumDays != null && newNumDays != ChronoUnit.DAYS.between(newStartDate, newEndDate) + 1) {
                logger.log(Level.WARNING, "Number of days does not match the start and end date");
                Ui.printInvalidModificationOfDate();
            } else {
                logger.log(Level.INFO, "Modifying trip dates");
                trip.setStartDate(newStartDate);
                trip.setEndDate(newEndDate);
                if (newNumDays != null) {
                    trip.setNumDays(newNumDays);
                }
                // method is called the correct the actual size of the Days arraylist
                trip.updateItinerarySize();
            }

            // Modify the total budget of the trip, and add the budget to each day averagely if not null
            boolean budgetIsModified = false;
            if (command.getTotalBudget() != null) {
                logger.log(Level.INFO, "Modifying trip total budget");
                budgetIsModified = true;
                trip.setTotalBudget(command.getTotalBudget());
            }

            Ui.printModifyTripMessage(trip.abbrInfo());
            // Show the user information about the current trip budget status if it is updated
            if (budgetIsModified) {
                trip.printBudgetStatus();
            }
            Ui.printNextCommandMessage();
        } catch (InvalidIndex e) {
            logger.log(Level.WARNING, "Index out of bounds");
            Ui.printIndexOutOfBounds();
        }
        logger.log(Level.INFO, "Finished executeModifyTrip");
    }

    private static void executeModifyCurTrip(TripsCommand command) {
        logger.log(Level.INFO, "Starting executeModifyCurTrip");
        try {
            Trip trip = trips.get(PARSER.getCurrentTrip());

            assert (trip != null
                    && (command.getName() != null
                    || command.getStartDate() != null
                    || command.getEndDate() != null
                    || command.getTotalBudget() != null));

            if (command.getName() != null) {
                logger.log(Level.INFO, "Modifying trip name");
                trip.setName(command.getName());
            }

            // Checking for the validity of the date modification
            LocalDate newStartDate = (command.getStartDate() != null) ? command.getStartDate() : trip.getStartDate();
            LocalDate newEndDate = (command.getEndDate() != null) ? command.getEndDate() : trip.getEndDate();
            Integer newNumDays = command.getNumDay();
            if (newNumDays != null && newNumDays != ChronoUnit.DAYS.between(newStartDate, newEndDate) + 1) {
                logger.log(Level.WARNING, "Number of days does not match the start and end date");
                Ui.printInvalidModificationOfDate();
            } else {
                logger.log(Level.INFO, "Modifying trip dates");
                trip.setStartDate(newStartDate);
                trip.setEndDate(newEndDate);
                if (newNumDays != null) {
                    trip.setNumDays(newNumDays);
                }
                // method is called the correct the actual size of the Days arraylist
                trip.updateItinerarySize();
            }

            // Modify the total budget of the trip, and add the budget to each day averagely if not null
            boolean budgetIsModified = false;
            if (command.getTotalBudget() != null) {
                logger.log(Level.INFO, "Modifying trip total budget");
                budgetIsModified = true;
                trip.setTotalBudget(command.getTotalBudget());

            }

            Ui.printModifyTripMessage(trip.abbrInfo());
            // Show the user information about the current trip budget status if it is updated
            trip.printBudgetStatus();
            Ui.printNextCommandMessage();

        } catch (TripNotFoundException e) {
            logger.log(Level.WARNING, "Trip not found");
            Ui.printTripNotFound();
        }
        logger.log(Level.INFO, "Finished executeModifyCurTrip");
    }


    private static void executeModifyAccommodation(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting executeModifyAccommodation");
        trips.get(command.getTrip()).modifyAccommodation(command.getName(), command.getBudget(),
                command.getDays(), command.getIndex());
        logger.log(Level.INFO, "Finished executeModifyAccommodation");
    }
}
