package voyatrip.command.types;

import java.util.ArrayList;
import java.util.Arrays;

import voyatrip.command.exceptions.InvalidArgumentKeyword;
import voyatrip.command.exceptions.InvalidArgumentValue;
import voyatrip.command.exceptions.InvalidDateFormat;
import voyatrip.command.exceptions.InvalidNumberFormat;
import voyatrip.command.exceptions.MissingArgument;

public class TransportationCommand extends Command {
    static final String[] INVALID_NAMES = {"all"};

    private String trip;
    private String name;
    private String mode;
    private Integer budget;
    private Integer index;
    private Integer day;

    public TransportationCommand(CommandAction commandAction,
                                 CommandTarget commandTarget,
                                 String trip,
                                 ArrayList<String> arguments)
            throws InvalidArgumentKeyword,
            InvalidArgumentValue,
            InvalidDateFormat,
            InvalidNumberFormat,
            MissingArgument {
        super(commandAction, commandTarget);
        this.trip = trip;
        name = null;
        mode = null;
        budget = null;
        index = null;
        day = null;

        processRawArgument(arguments);
    }

    @Override
    protected void processRawArgument(ArrayList<String> arguments)
            throws InvalidArgumentKeyword,
            InvalidArgumentValue,
            InvalidDateFormat,
            InvalidNumberFormat,
            MissingArgument {
        super.processRawArgument(arguments);

        processCommandVariation();
    }

    private void processCommandVariation() {
        if (commandAction == CommandAction.DELETE_BY_INDEX && name != null) {
            super.setCommandAction(CommandAction.DELETE_BY_NAME);
        } else if (commandAction == CommandAction.LIST) {
            if (name != null) {
                super.setCommandAction(CommandAction.LIST_BY_NAME);
            } else if (index != null) {
                super.setCommandAction(CommandAction.LIST_BY_INDEX);
            }
        }
    }

    @Override
    protected void matchArgument(String argument)
            throws InvalidArgumentKeyword, InvalidNumberFormat, InvalidArgumentValue {
        String argumentKeyword = argument.split("\\s+")[0];
        String argumentValue = argument.replaceFirst(argumentKeyword, "").strip();
        argumentKeyword = argumentKeyword.toLowerCase();

        if (!argumentKeyword.equals("all") && argumentValue.isEmpty()) {
            throw new InvalidArgumentValue();
        }

        try {
            switch (argumentKeyword) {
            case "name", "n" -> name = argumentValue;
            case "mode", "m" -> mode = argumentValue;
            case "budget", "b" -> budget = Integer.parseInt(argumentValue);
            case "index", "i" -> index = Integer.parseInt(argumentValue);
            case "day", "d" -> day = Integer.parseInt(argumentValue);
            case "all" -> name = "all";
            default -> throw new InvalidArgumentKeyword();
            }
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormat();
        }
    }

    @Override
    protected void validateArgument() throws InvalidArgumentValue, MissingArgument {
        boolean isAdd = commandAction == CommandAction.ADD;
        boolean isDelete = commandAction == CommandAction.DELETE_BY_INDEX ||
                commandAction == CommandAction.DELETE_BY_NAME;
        boolean isModify = commandAction == CommandAction.MODIFY;
        boolean isList = commandAction == CommandAction.LIST;

        boolean isMissingAddArgument =
                name == null || mode == null || budget == null || day == null;
        boolean isMissingDeleteArgument = name == null && index == null;
        boolean isMissingModifyArgument = index == null ||
                (name == null && mode == null && budget == null && day == null);
        boolean isMissingListArgument = name == null && index == null;

        if (isAdd && isMissingAddArgument ||
                isDelete && isMissingDeleteArgument ||
                isModify && isMissingModifyArgument ||
                isList && isMissingListArgument) {
            throw new MissingArgument();
        }

        boolean isInvalidBudget = budget != null && budget < 0;
        boolean isInvalidName = !isList && name != null && Arrays.asList(INVALID_NAMES).contains(name);

        if (isInvalidBudget || isInvalidName) {
            throw new InvalidArgumentValue();
        }
    }

    public String getTrip() {
        return trip;
    }

    public String getName() {
        return name;
    }

    public String getMode() {
        return mode;
    }

    public Integer getBudget() {
        return budget;
    }

    public Integer getIndex() {
        return index;
    }

    public Integer getDay() {
        return day;
    }
}
