package voyatrip.logic.executor;

import voyatrip.model.Trip;
import voyatrip.model.TripList;

import voyatrip.logic.command.Parser;
import voyatrip.logic.command.exceptions.AccommodationNotFound;
import voyatrip.logic.command.exceptions.ActivityNotFound;
import voyatrip.logic.command.exceptions.DuplicatedName;
import voyatrip.logic.command.exceptions.InvalidArgumentKeyword;
import voyatrip.logic.command.exceptions.InvalidArgumentValue;
import voyatrip.logic.command.exceptions.InvalidBudget;
import voyatrip.logic.command.exceptions.InvalidCommand;
import voyatrip.logic.command.exceptions.InvalidDay;
import voyatrip.logic.command.exceptions.InvalidCommandAction;
import voyatrip.logic.command.exceptions.InvalidCommandTarget;
import voyatrip.logic.command.exceptions.InvalidDuplicateActivity;
import voyatrip.logic.command.exceptions.InvalidDate;
import voyatrip.logic.command.exceptions.InvalidName;
import voyatrip.logic.command.exceptions.InvalidNumberFormat;
import voyatrip.logic.command.exceptions.InvalidTimeFormat;
import voyatrip.logic.command.exceptions.MissingArgument;
import voyatrip.logic.command.exceptions.MissingCommandKeyword;
import voyatrip.logic.command.exceptions.InvalidIndex;
import voyatrip.logic.command.exceptions.TransportationNotFound;
import voyatrip.logic.command.exceptions.TripNotFoundException;
import voyatrip.logic.command.types.AccommodationCommand;
import voyatrip.logic.command.types.Command;
import voyatrip.logic.command.types.CommandAction;
import voyatrip.logic.command.types.CommandTarget;
import voyatrip.logic.command.types.ItineraryCommand;
import voyatrip.logic.command.types.TransportationCommand;
import voyatrip.logic.command.types.TripsCommand;

import voyatrip.ui.Ui;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

import static voyatrip.ui.Ui.readInput;

/**
 * This class is the main executor of the VoyaTrip application.
 * It handles the execution of commands
 */
public class Executor {
    public static TripList trips = new TripList();
    static Boolean isExit = false;
    static final Parser PARSER = new Parser();
    private static final Logger logger = Logger.getLogger(voyatrip.VoyaTrip.class.getName());

    public static void run(TripList trips) {
        Executor.trips = trips;
        logger.log(Level.INFO, "Starting VoyaTrip application");
        Ui.printWelcomeMessage();
        while (!isExit) {
            Ui.printCurrentPath(PARSER);
            handleInput(readInput());
            Ui.printNextCommandMessage();
        }
        Ui.printGoodbyeMessage();
    }

    private static void handleInput(String input) {
        try {
            logger.log(Level.INFO, "Starting handleInput");
            Command command = PARSER.parse(input);
            handleCommand(command);
            logger.log(Level.INFO, "Finished handleInput");
        } catch (AccommodationNotFound e) {
            logger.log(Level.WARNING, "Accommodation not found");
            Ui.printAccommodationNotFound();
        } catch (ActivityNotFound e) {
            logger.log(Level.WARNING, "Activity not found");
            Ui.printActivityNotFound();
        } catch (DuplicatedName e) {
            logger.log(Level.WARNING, "Duplicated name");
            Ui.printDuplicatedName();
        } catch (InvalidArgumentKeyword e) {
            logger.log(Level.WARNING, "Invalid argument keyword");
            Ui.printInvalidArgumentKeyword();
        } catch (InvalidBudget e) {
            logger.log(Level.WARNING, "Invalid budget");
            Ui.printInvalidBudget();
        } catch (InvalidCommandAction e) {
            logger.log(Level.WARNING, "Invalid command action");
            Ui.printInvalidCommandAction();
        } catch (InvalidCommandTarget e) {
            logger.log(Level.WARNING, "Invalid command target");
            Ui.printInvalidCommandTarget();
        } catch (InvalidDate e) {
            logger.log(Level.WARNING, "Invalid date");
            Ui.printInvalidDate();
        } catch (InvalidDay e) {
            logger.log(Level.WARNING, "Invalid day");
            Ui.printInvalidDay();
        } catch (InvalidIndex e) {
            logger.log(Level.WARNING, "Invalid index");
            Ui.printInvalidIndex();
        } catch (InvalidName e) {
            logger.log(Level.WARNING, "Invalid name");
            Ui.printInvalidName();
        } catch (InvalidNumberFormat e) {
            logger.log(Level.WARNING, "Invalid number format");
            Ui.printInvalidNumberFormat();
        } catch (MissingArgument e) {
            logger.log(Level.WARNING, "Missing argument");
            Ui.printMissingArgument();
        } catch (MissingCommandKeyword e) {
            logger.log(Level.WARNING, "Missing command keyword");
            Ui.printMissingCommandKeyword();
        } catch (TransportationNotFound e) {
            logger.log(Level.WARNING, "Trip not found");
            Ui.printTransportationNotFound();
        } catch (TripNotFoundException e) {
            logger.log(Level.WARNING, "Trip not found");
            Ui.printTripNotFound();
        } catch (InvalidTimeFormat e) {
            logger.log(Level.WARNING, "Invalid time format");
            Ui.printInvalidTimeFormat();
        } catch (InvalidDuplicateActivity e) {
            logger.log(Level.WARNING, "Duplicate activity");
            Ui.printInvalidDuplicateActivity();
        } catch (InvalidArgumentValue e) {
            logger.log(Level.WARNING, "Invalid argument value");
            Ui.printInvalidArgumentValue();
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
        default -> throw new InvalidCommandTarget();
        }
    }

    private static void handleTrip(TripsCommand command) throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting handleTrip");
        switch (command.getCommandAction()) {
        case ADD -> executeAddTrip(command);
        case DELETE_BY_INDEX -> executeDeleteTripByIndex(command);
        case DELETE_BY_NAME -> executeDeleteTripByName(command);
        case LIST_BY_INDEX -> executeListTripByIndex(command);
        case LIST_BY_NAME -> executeListTripByName(command);
        case CHANGE_TRIP_BY_NAME -> executeChangeDirectoryTripByName(command);
        case CHANGE_TRIP_BY_INDEX -> executeChangeDirectoryTripByIndex(command);
        case MODIFY -> executeModifyTrip(command);
        case MODIFY_TRIP_WITHOUT_INDEX -> executeModifyCurTrip(command);
        default -> throw new InvalidCommandAction();
        }
        logger.log(Level.INFO, "Finished handleTrip");
    }

    private static void handleItinerary(ItineraryCommand command) throws InvalidCommand {
        switch (command.getCommandAction()) {
        case LIST -> executeListItinerary(command);
        case CHANGE_DIRECTORY -> executeChangeDirectoryItinerary(command);
        default -> throw new InvalidCommandAction();
        }
    }

    private static void handleActivity(ItineraryCommand command) throws InvalidCommand, TripNotFoundException {
        switch (command.getCommandAction()) {
        case ADD -> executeAddActivity(command);
        case DELETE_BY_INDEX -> executeDeleteActivityByIndex(command);
        case DELETE_BY_NAME -> executeDeleteActivityByName(command);
        default -> throw new InvalidCommandAction();
        }
    }

    private static void handleAccommodation(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        switch (command.getCommandAction()) {
        case ADD -> executeAddAccommodation(command);
        case DELETE_BY_INDEX -> executeDeleteAccommodationByIndex(command);
        case DELETE_BY_NAME -> executeDeleteAccommodationByName(command);
        case LIST_BY_INDEX -> executeListAccommodationByIndex(command);
        case LIST_BY_NAME -> executeListAccommodationByName(command);
        case CHANGE_DIRECTORY -> executeChangeDirectoryAccommodation(command);
        case MODIFY -> executeModifyAccommodation(command);
        default -> throw new InvalidCommandAction();
        }
    }

    private static void handleTransportation(TransportationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting handleTransportation");
        switch (command.getCommandAction()) {
        case ADD -> executeAddTransportation(command);
        case DELETE_BY_INDEX -> executeDeleteTransportationByIndex(command);
        case DELETE_BY_NAME -> executeDeleteTransportationByName(command);
        case LIST_BY_INDEX -> executeListTransportationByIndex(command);
        case LIST_BY_NAME -> executeListTransportationByName(command);
        case MODIFY -> executeModifyTransportation(command);
        case CHANGE_DIRECTORY -> executeChangeDirectoryTransportation(command);
        default -> {
            logger.log(Level.WARNING, "Unknown command action: " + command.getCommandAction());
            throw new InvalidCommandAction();
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

    private static void executeAddActivity(ItineraryCommand command) throws InvalidCommand {
        trips.get(command.getTrip()).addActivity(command.getDay(), command.getName(), command.getTime());
    }

    private static void executeAddAccommodation(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting executeAddAccommodation");
        trips.get(command.getTrip()).addAccommodation(command.getName(), command.getBudget(), command.getDays());
        logger.log(Level.INFO, "Finished executeAddAccommodation");
    }

    private static void executeAddTransportation(TransportationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeAddTransportation");
        trips.get(command.getTrip()).addTransportation(command.getName(), command.getMode(), command.getBudget(),
                command.getDay());
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

    private static void executeDeleteActivityByIndex(ItineraryCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeDeleteActivityByIndex");
        trips.get(command.getTrip()).deleteActivity(command.getDay(), command.getIndex());
        logger.log(Level.INFO, "Finished executeDeleteActivityByIndex");
    }

    private static void executeDeleteActivityByName(ItineraryCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeDeleteActivityByName");
        trips.get(command.getTrip()).deleteActivity(command.getDay(), command.getName());
        logger.log(Level.INFO, "Finished executeDeleteActivityByName");
    }

    private static void executeDeleteAccommodationByIndex(AccommodationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeDeleteAccommodationByIndex");
        trips.get(command.getTrip()).deleteAccommodation(command.getIndex());
        logger.log(Level.INFO, "Finished executeDeleteAccommodationByIndex");
    }

    private static void executeDeleteAccommodationByName(AccommodationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeDeleteAccommodationByName");
        trips.get(command.getTrip()).deleteAccommodation(command.getName());
        logger.log(Level.INFO, "Finished executeDeleteAccommodationByName");
    }

    private static void executeDeleteTransportationByIndex(TransportationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeDeleteTransportationByIndex");
        getCurrentTrip(command).deleteTransportation(command.getIndex());
        logger.log(Level.INFO, "Finished executeDeleteTransportationByIndex");
    }

    private static void executeDeleteTransportationByName(TransportationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeDeleteTransportationByName");
        getCurrentTrip(command).deleteTransportation(command.getName());
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

    private static void executeListItinerary(ItineraryCommand command) throws TripNotFoundException {
        logger.log(Level.INFO, "Starting executeListItinerary");
        Ui.printItinerary(trips.get(command.getTrip()));
        logger.log(Level.INFO, "Finished executeListItinerary");
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

    private static void executeListTransportationByIndex(TransportationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeListTransportationByIndex");
        getCurrentTrip(command).listTransportation(command.getIndex());
        logger.log(Level.INFO, "Finished executeListTransportationByIndex");
    }

    private static void executeListTransportationByName(TransportationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeListTransportation");
        if ("all".equals(String.valueOf(command.getName()))) {
            Ui.printTransportationList(getCurrentTrip(command));
        } else {
            getCurrentTrip(command).listTransportation(command.getName());
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

    /**
     * Modify the trip with the given command
     *
     * @param command The command to modify the trip
     *                Note that if the size of the modified numsDay is different from the original,
     *                the size of the itinerary will be added or removed at the end accordingly
     */
    private static void executeModifyTrip(TripsCommand command) {
        logger.log(Level.INFO, "Starting executeModifyTrip");
        try {
            modifyTrip(command, trips.get(command.getIndex()));
        } catch (InvalidIndex e) {
            logger.log(Level.WARNING, "Invalid index");
            Ui.printInvalidIndex();
        }
        logger.log(Level.INFO, "Finished executeModifyTrip");
    }

    /**
     * Modify the current trip with the given command
     *
     * @param command The command to modify the trip
     */
    private static void executeModifyCurTrip(TripsCommand command) {
        logger.log(Level.INFO, "Starting executeModifyCurTrip");
        try {
            modifyTrip(command, trips.get(PARSER.getCurrentTrip()));
        } catch (TripNotFoundException e) {
            logger.log(Level.WARNING, "Trip not found");
            Ui.printTripNotFound();
        } catch (InvalidIndex e) {
            logger.log(Level.WARNING, "Invalid index");
            Ui.printInvalidIndex();
        }
        logger.log(Level.INFO, "Finished executeModifyCurTrip");
    }

    /**
     * Helper function for exeucuteModifyTrip and executeModifyCurTrip
     *
     * @param command The command to modify the trip
     * @param trip    The trip to be modified
     */
    private static void modifyTrip(TripsCommand command, Trip trip) throws InvalidIndex {
        Boolean isNameModified = false;
        Boolean isStartDateModified = false;
        Boolean isEndDateModified = false;
        Boolean isBudgetModified = false;


        assert (trip != null
                && (command.getName() != null
                || command.getStartDate() != null
                || command.getEndDate() != null
                || command.getTotalBudget() != null));

        if (command.getName() != null) {
            if (!trip.getName().equals(command.getName())) {
                logger.log(Level.INFO, "Modifying trip name");
                trip.setName(command.getName());
                isNameModified = true;
            } else {
                logger.log(Level.WARNING, "Trip name is the same");
                Ui.printSameTripNameMessage();
            }
        }

        // Modify the date
        if (command.getStartDate() != null && command.getEndDate() == null) {
            if (trip.getStartDate().equals(command.getStartDate())) {
                logger.log(Level.INFO, "Trip start date is the same");
                Ui.printSameTripStartDateMessage();
            } else if (trip.getEndDate().isBefore(command.getStartDate())) {
                logger.log(Level.INFO, "Trip start date is after end date");
                Ui.printInvalidModificationOfDate();
            } else {
                logger.log(Level.INFO, "Modifying trip start date");
                trip.setStartDate(command.getStartDate());
                isStartDateModified = true;
            }
        } else if (command.getStartDate() == null && command.getEndDate() != null) {
            if (trip.getEndDate().equals(command.getEndDate())) {
                logger.log(Level.INFO, "Trip end date is the same");
                Ui.printSameTripEndDateMessage();
            } else if (trip.getStartDate().isAfter(command.getEndDate())) {
                logger.log(Level.INFO, "Trip end date is before start date");
                Ui.printInvalidModificationOfDate();
            } else {
                logger.log(Level.INFO, "Modifying trip end date");
                trip.setEndDate(command.getEndDate());
                isEndDateModified = true;
            }
        } else if (command.getStartDate() != null && command.getEndDate() != null) {
            if (trip.getStartDate().equals(command.getStartDate())
                    && trip.getEndDate().equals(command.getEndDate())) {
                logger.log(Level.INFO, "Trip start date and end date are the same");
                Ui.printInvalidModificationOfDate();
            } else if (trip.getStartDate().isAfter(command.getEndDate())) {
                logger.log(Level.INFO, "Trip start date is after end date");
                Ui.printInvalidModificationOfDate();
            } else {
                logger.log(Level.INFO, "Modifying trip start and end date");
                trip.setStartDate(command.getStartDate());
                trip.setEndDate(command.getEndDate());
                isStartDateModified = true;
                isEndDateModified = true;
            }
        }

        if (isStartDateModified || isEndDateModified) {
            trip.updateItinerarySize();
        }

        // Modify the total budget of the trip, and add the budget to each day averagely if not null
        if (command.getTotalBudget() != null) {
            if (trip.getTotalBudget().equals(command.getTotalBudget())) {
                logger.log(Level.INFO, "Trip budget is the same");
                Ui.printSameTripBudgetMessage();
            } else {
                logger.log(Level.INFO, "Modifying trip total budget");
                trip.setTotalBudget(command.getTotalBudget());
                isBudgetModified = true;
            }
        }

        if (!isNameModified && !isStartDateModified && !isEndDateModified && !isBudgetModified) {
            logger.log(Level.WARNING, "Trip modification is the same");
            Ui.printSameTripMessage();
        } else {
            Ui.printModifyTripMessage(trip.abbrInfo());
        }

        // Show the user information about the current trip budget status if it is updated
        if (isBudgetModified) {
            trip.printBudgetStatus();
        }
    }

    private static void executeModifyAccommodation(AccommodationCommand command)
            throws InvalidCommand, TripNotFoundException {
        logger.log(Level.INFO, "Starting executeModifyAccommodation");
        trips.get(command.getTrip()).modifyAccommodation(command.getName(), command.getBudget(),
                command.getDays(), command.getIndex());
        logger.log(Level.INFO, "Finished executeModifyAccommodation");
    }

    /*
     * Execute modifyTransportation method with the given command
     *
     * @param command The command to modify the trip
     */
    private static void executeModifyTransportation(TransportationCommand command) throws InvalidCommand {
        logger.log(Level.INFO, "Starting executeModifyTransportation");
        getCurrentTrip(command).modifyTransportation(command.getName(), command.getMode(), command.getBudget(),
                command.getDay(), command.getIndex());
        logger.log(Level.INFO, "Finished executeModifyTransportation");
    }

    private static Trip getCurrentTrip(TransportationCommand command) throws TripNotFoundException {
        return trips.get(command.getTrip());
    }
}
