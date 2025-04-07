package voyatrip;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.Scanner;
import java.util.logging.Logger;
import java.util.logging.Level;

import voyatrip.command.exceptions.AccommodationNotFound;
import voyatrip.command.exceptions.ActivityNotFound;
import voyatrip.command.exceptions.DuplicatedName;
import voyatrip.command.exceptions.InvalidArgumentKeyword;
import voyatrip.command.exceptions.InvalidArgumentValue;
import voyatrip.command.exceptions.InvalidBudget;
import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.InvalidDay;
import voyatrip.command.exceptions.InvalidCommandAction;
import voyatrip.command.exceptions.InvalidCommandTarget;
import voyatrip.command.exceptions.InvalidDuplicateActivity;
import voyatrip.command.exceptions.InvalidDate;
import voyatrip.command.exceptions.InvalidName;
import voyatrip.command.exceptions.InvalidNumberFormat;
import voyatrip.command.exceptions.InvalidScope;
import voyatrip.command.exceptions.InvalidTimeFormat;
import voyatrip.command.exceptions.MissingArgument;
import voyatrip.command.exceptions.MissingCommandKeyword;
import voyatrip.command.exceptions.InvalidIndex;
import voyatrip.command.exceptions.TransportationNotFound;
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

    /*
     * This method is used to check if the application is running in end user mode.
     */
    public static Boolean isEndUserMode(String[] args) {
        return !Arrays.asList(args).contains("--verbose");
    }

    /*
     * This method is used to set the logger for the application.
     * If it is the user, then the logger will be disabled.
     */
    public static void setLogger(String[] args) {
        if (isEndUserMode(args)) {
            Logger.getLogger("").setLevel(Level.OFF);
        }
    }

    public static void main(String[] args) {
        setLogger(args);
        load_json();
        run();
        save_json();
    }

    /**
     * This method is used to load the json file for the application.
     */
    public static void load_json() {
        Storage.load().ifPresent(tripList -> {
            trips = tripList;
        });
    }

    /**
     * This method is used to save the json file for the application.
     */
    public static void save_json() {
        Storage.save(trips);
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
        } catch (InvalidScope e) {
            logger.log(Level.WARNING, "Invalid scope");
            Ui.printInvalidScope();
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
        } catch (InvalidTimeFormat e){
            logger.log(Level.WARNING, "Invalid time format");
            Ui.printInvalidTimeFormat();
        }  catch (InvalidDuplicateActivity e){
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

    private static void executeAddTransportation(TransportationCommand command) throws InvalidCommand{
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
     * @param command The command to modify the trip
     *     Note that if the size of the modified numsDay is different from the original,
     *     the size of the itinerary will be added or removed at the end accordingly
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
     * @param command The command to modify the trip
     */
    private static void executeModifyCurTrip(TripsCommand command) {
        logger.log(Level.INFO, "Starting executeModifyCurTrip");
        try {
            modifyTrip(command, trips.get(PARSER.getCurrentTrip()));
        } catch (TripNotFoundException e) {
            logger.log(Level.WARNING, "Trip not found");
            Ui.printTripNotFound();
        }
        logger.log(Level.INFO, "Finished executeModifyCurTrip");
    }

    /**
     * Helper function for exeucuteModifyTrip and executeModifyCurTrip
     * @param command The command to modify the trip
     * @param trip The trip to be modified
     */
    private static void modifyTrip(TripsCommand command, Trip trip) {
        Boolean isNameSame = false;
        Boolean isDatesSame = false;
        Boolean isInvalidDate = false;
        Boolean isNumDaysSame = false;
        Boolean isBudgetSame = false;


        assert (trip != null
                && (command.getName() != null
                || command.getStartDate() != null
                || command.getEndDate() != null
                || command.getTotalBudget() != null));

        if (command.getName() != null) {
            // check if the trip name is the same as the original
            if (trip.getName().equals(command.getName())) {
                logger.log(Level.WARNING, "Trip name is the same");
                isNameSame = true;
                Ui.printSameTripNameMessage();
            } else {
                logger.log(Level.INFO, "Modifying trip name");
                trip.setName(command.getName());
            }
        }

        // Checking for the validity of the date modification
        LocalDate newStartDate = (command.getStartDate() != null) ? command.getStartDate() : trip.getStartDate();
        LocalDate newEndDate = (command.getEndDate() != null) ? command.getEndDate() : trip.getEndDate();
        Integer newNumDays = command.getNumDay();

        if (newNumDays != null && newNumDays != ChronoUnit.DAYS.between(newStartDate, newEndDate) + 1) {
            logger.log(Level.INFO, "Number of days does not match the start and end date");
            isInvalidDate = true;
            Ui.printInvalidModificationOfDate();
        }

        if (trip.getStartDate().equals(newStartDate)
                && trip.getEndDate().equals(newEndDate)) {
            logger.log(Level.INFO, "Trip dates are the same");
            isDatesSame = true;
            Ui.printSameTripDatesMessage();
        }
        if (trip.getNumDays() == newNumDays) {
            logger.log(Level.WARNING, "Trip number of days is the same");
            isNumDaysSame = true;
            Ui.printSameTripNumDaysMessage();
        }
        if (!isInvalidDate && (!isDatesSame || !isNumDaysSame)) {
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
        if (command.getTotalBudget() != null) {
            if (trip.getTotalBudget().equals(command.getTotalBudget())) {
                logger.log(Level.WARNING, "Trip budget is the same");
                isBudgetSame = true;
                Ui.printSameTripBudgetMessage();
            }
            logger.log(Level.INFO, "Modifying trip total budget");
            trip.setTotalBudget(command.getTotalBudget());
        }

        if (isNameSame && isDatesSame && isNumDaysSame && isBudgetSame) {
            logger.log(Level.WARNING, "Trip modification is the same");
            Ui.printSameTripMessage();
        } else {
            Ui.printModifyTripMessage(trip.abbrInfo());
        }

        // Show the user information about the current trip budget status if it is updated
        if (!isBudgetSame) {
            trip.printBudgetStatus();
        }
        Ui.printNextCommandMessage();
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
