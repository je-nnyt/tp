package voyatrip.command.types;

import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidArgumentKeyword;
import voyatrip.command.exceptions.InvalidDateFormat;
import voyatrip.command.exceptions.InvalidNumberFormat;
import voyatrip.command.exceptions.MissingArgument;

public class AccommodationCommand extends Command {
    private String trip;
    private String name;
    private Integer budget;
    private Integer index;

    public AccommodationCommand(CommandAction commandAction,
                                CommandTarget commandTarget,
                                String trip,
                                ArrayList<String> arguments)
            throws InvalidArgumentKeyword, InvalidNumberFormat, InvalidDateFormat, MissingArgument {
        super(commandAction, commandTarget);
        this.trip = trip;
        name = null;
        budget = null;
        index = null;

        processRawArgument(arguments);
    }

    @Override
    protected void processRawArgument(ArrayList<String> arguments)
            throws InvalidArgumentKeyword, InvalidNumberFormat, InvalidDateFormat, MissingArgument {
        super.processRawArgument(arguments);

        if (commandAction == CommandAction.DELETE_BY_INDEX && name != null) {
            super.setCommandAction(CommandAction.DELETE_BY_NAME);
        }
    }

    @Override
    protected void matchArgument(String argument) throws InvalidArgumentKeyword, InvalidNumberFormat {
        String argumentKeyword = argument.split("\\s+")[0];
        String argumentValue = argument.replaceFirst(argumentKeyword, "").strip();
        argumentKeyword = argumentKeyword.toLowerCase();

        try {
            switch (argumentKeyword) {
            case "name", "n" -> name = argumentValue;
            case "budget", "b" -> budget = Integer.parseInt(argumentValue);
            case "index", "i" -> index = Integer.parseInt(argumentValue);
            default -> throw new InvalidArgumentKeyword();
            }
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormat();
        }
    }

    @Override
    protected boolean isMissingArgument() {
        boolean isInvalidName = name == null;
        boolean isInvalidAdd = isInvalidName || budget == null;
        boolean isInvalidDelete = isInvalidName && index == null;

        return switch (commandAction) {
        case ADD -> isInvalidAdd;
        case DELETE_BY_INDEX, DELETE_BY_NAME -> isInvalidDelete;
        case LIST, CHANGE_DIRECTORY, EXIT -> false;
        default -> true;
        };
    }

    public String getTrip() {
        return trip;
    }

    public String getName() {
        return name;
    }

    public Integer getBudget() {
        return budget;
    }

    public Integer getIndex() {
        return index;
    }
}
