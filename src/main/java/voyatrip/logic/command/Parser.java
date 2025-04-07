package voyatrip.logic.command;

import java.util.ArrayList;

import voyatrip.logic.command.exceptions.InvalidArgumentKeyword;
import voyatrip.logic.command.exceptions.InvalidArgumentValue;
import voyatrip.logic.command.exceptions.InvalidCommandAction;
import voyatrip.logic.command.exceptions.InvalidCommandTarget;
import voyatrip.logic.command.exceptions.InvalidDate;
import voyatrip.logic.command.exceptions.InvalidNumberFormat;
import voyatrip.logic.command.exceptions.InvalidTimeFormat;
import voyatrip.logic.command.exceptions.MissingArgument;
import voyatrip.logic.command.exceptions.MissingCommandKeyword;
import voyatrip.logic.command.types.AccommodationCommand;
import voyatrip.logic.command.types.CommandAction;
import voyatrip.logic.command.types.CommandTarget;
import voyatrip.logic.command.types.ExitCommand;
import voyatrip.logic.command.types.ItineraryCommand;
import voyatrip.logic.command.types.Command;
import voyatrip.logic.command.types.TransportationCommand;
import voyatrip.logic.command.types.TripsCommand;

public class Parser {
    private CommandTarget currentTarget;
    private String currentTrip;

    public Parser() {
        currentTarget = CommandTarget.TRIP;
        currentTrip = "";
    }

    /**
     * Set the current target page the user is in.
     *
     * @param target The current target page the user is in.
     */
    public void setCurrentTarget(CommandTarget target) {
        currentTarget = target;
    }

    /**
     * Set the current trip the user is in.
     *
     * @param trip The current trip the user is in.
     */
    public void setCurrentTrip(String trip) {
        currentTrip = trip;
    }

    public String getCurrentPath() {
        if (currentTarget == CommandTarget.TRIP) {
            return "~ >";
        } else {
            return "~/" + currentTrip + "/" + currentTarget + " >";
        }
    }

    public String getCurrentTrip() {
        return currentTrip;
    }

    /**
     * Parse the input command.
     * The return is an abstract Command object that represents the input command.
     *
     * @param command Input command.
     * @return Command object that represents the input command.
     * @throws InvalidArgumentKeyword If the argument keyword is invalid.
     * @throws InvalidArgumentValue If the argument value is invalid.
     * @throws InvalidCommandTarget If the command target is invalid.
     * @throws InvalidCommandAction If the command action is invalid.
     * @throws InvalidDate If the date format is invalid.
     * @throws InvalidNumberFormat If the number format is invalid.
     * @throws MissingArgument If there is missing argument.
     * @throws MissingCommandKeyword If there is missing command keyword.
     */
    public Command parse(String command)
            throws InvalidArgumentKeyword,
            InvalidArgumentValue,
            InvalidCommandTarget,
            InvalidCommandAction,
            InvalidDate,
            InvalidNumberFormat,
            MissingArgument,
            MissingCommandKeyword,
            InvalidTimeFormat {
        CommandAction commandAction = extractCommandAction(command);

        // Exception case: no argument command exit
        if (commandAction.equals(CommandAction.EXIT)) {
            return new ExitCommand();
        }

        ArrayList<String> arguments = extractCommandArguments(command);
        CommandTarget commandTarget = extractCommandTargetType(command, commandAction);

        // Exception case: modify trip within the corresponding trip directory
        if (commandTarget == CommandTarget.TRIP && commandAction == CommandAction.MODIFY && !currentTrip.isEmpty()) {
            commandAction = CommandAction.MODIFY_TRIP_WITHOUT_INDEX;
        }

        validateTarget(commandTarget);

        return matchCommand(commandAction, commandTarget, arguments);
    }

    private ArrayList<String> extractCommandArguments(String command) {
        String[] doubleHyphenSeparatedTokens = command.strip().split("(^--|\\s+--)(?=\\w+)");
        ArrayList<String> arguments = new ArrayList<>();
        for (int i = 1; i < doubleHyphenSeparatedTokens.length; i++) {
            if (!doubleHyphenSeparatedTokens[i].isEmpty()) {
                arguments.add(doubleHyphenSeparatedTokens[i]);
            }
        }
        return arguments;
    }

    private CommandAction extractCommandAction(String command) throws MissingCommandKeyword, InvalidCommandAction {
        String commandAction = null;
        try {
            commandAction = command.strip().split("\\s+")[0].toLowerCase();
        } catch (IndexOutOfBoundsException e) {
            throw new MissingCommandKeyword();
        }
        return switch (commandAction) {
        case "add", "a", "make", "mk" -> CommandAction.ADD;
        case "delete", "d", "remove", "rm" -> CommandAction.DELETE_BY_INDEX;
        case "modify", "mod", "m" -> CommandAction.MODIFY;
        case "list", "l" -> CommandAction.LIST;
        case "cd" -> CommandAction.CHANGE_DIRECTORY;
        case "exit", "quit", "bye" -> CommandAction.EXIT;
        default -> throw new InvalidCommandAction();
        };
    }

    private CommandTarget extractCommandTargetType(String command, CommandAction commandAction)
            throws InvalidCommandTarget {
        String[] spaceSeparatedTokens = command.strip().split("\\s+");
        if (spaceSeparatedTokens.length == 1) {
            return currentTarget;
        }

        String commandTarget = spaceSeparatedTokens[1].toLowerCase();

        // exception case: cd ..
        if (commandAction.equals(CommandAction.CHANGE_DIRECTORY) && commandTarget.equals("..")) {
            return CommandTarget.TRIP;
        }

        return switch (commandTarget) {
        case "trip" -> CommandTarget.TRIP;
        case "itinerary", "itin", "i" -> CommandTarget.ITINERARY;
        case "activity", "act" -> CommandTarget.ACTIVITY;
        case "accommodation", "accom" -> CommandTarget.ACCOMMODATION;
        case "transportation", "tran" -> CommandTarget.TRANSPORTATION;
        default -> getAdjustedCurrentTarget(commandAction, commandTarget);
        };
    }

    private CommandTarget getAdjustedCurrentTarget(CommandAction action, String target) throws InvalidCommandTarget {
        if(!target.matches("--\\w+")) {
            throw new InvalidCommandTarget();
        }

        boolean isAddDeleteModify = action == CommandAction.ADD ||
                action == CommandAction.DELETE_BY_INDEX ||
                action == CommandAction.MODIFY;

        if (currentTarget == CommandTarget.ITINERARY && isAddDeleteModify) {
            return CommandTarget.ACTIVITY;
        } else {
            return currentTarget;
        }
    }

    private void validateTarget(CommandTarget commandTarget) throws InvalidCommandTarget {
        // target scope too small
        boolean isInvalidTarget = !commandTarget.equals(CommandTarget.TRIP) && currentTarget == CommandTarget.TRIP;

        if (isInvalidTarget) {
            throw new InvalidCommandTarget();
        }
    }

    private Command matchCommand(CommandAction commandAction, CommandTarget commandTarget, ArrayList<String> arguments)
            throws InvalidArgumentKeyword,
            InvalidArgumentValue,
            InvalidCommandTarget,
            InvalidDate,
            InvalidNumberFormat,
            MissingArgument,
            InvalidTimeFormat {
        return switch (commandTarget) {
        case TRIP -> new TripsCommand(commandAction, commandTarget, arguments);
        case ITINERARY, ACTIVITY -> new ItineraryCommand(commandAction, commandTarget, currentTrip, arguments);
        case ACCOMMODATION -> new AccommodationCommand(commandAction, commandTarget, currentTrip, arguments);
        case TRANSPORTATION -> new TransportationCommand(commandAction, commandTarget, currentTrip, arguments);
        default -> throw new InvalidCommandTarget();
        };
    }
}
