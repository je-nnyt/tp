package voyatrip.command.types;

import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidArgumentKeyword;
import voyatrip.command.exceptions.InvalidArgumentValue;
import voyatrip.command.exceptions.InvalidDateFormat;
import voyatrip.command.exceptions.InvalidNumberFormat;
import voyatrip.command.exceptions.MissingArgument;

public class TransportationCommand extends Command {
    private String trip;
    private String name;
    private String mode;
    private Integer budget;
    private Integer index;
    private Integer startDay;
    private Integer endDay;
    private ArrayList<Integer> days;

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
        startDay = null;
        endDay = null;
        days = new ArrayList<>();

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

        if (commandAction == CommandAction.DELETE_BY_INDEX && name != null) {
            super.setCommandAction(CommandAction.DELETE_BY_NAME);
        }
    }

    @Override
    protected void matchArgument(String argument)
            throws InvalidArgumentKeyword, InvalidNumberFormat, InvalidArgumentValue {
        String argumentKeyword = argument.split("\\s+")[0];
        String argumentValue = argument.replaceFirst(argumentKeyword, "").strip();
        argumentKeyword = argumentKeyword.toLowerCase();

        if (argumentValue.isEmpty()) {
            throw new InvalidArgumentValue();
        }

        try {
            switch (argumentKeyword) {
            case "name", "n" -> name = argumentValue;
            case "mode", "m" -> mode = argumentValue;
            case "budget", "b" -> budget = Integer.parseInt(argumentValue);
            case "index", "i" -> index = Integer.parseInt(argumentValue);
            case "all" -> name = "all";
            case "start", "s" -> startDay = Integer.parseInt(argumentValue);
            case "end", "e" -> endDay = Integer.parseInt(argumentValue);
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

        boolean isMissingAddArgument = name == null || mode == null || budget == null;
        boolean isMissingDeleteArgument = name == null && index == null;
        boolean isMissingModifyArgument = index == null || (name == null && mode == null && budget == null);

        if (isAdd && isMissingAddArgument ||
                isDelete && isMissingDeleteArgument ||
                isModify && isMissingModifyArgument) {
            throw new MissingArgument();
        }

        if (budget != null && budget < 0) {
            throw new InvalidArgumentValue();
        }
        if(startDay == null || startDay <= 0) {
            throw new InvalidArgumentValue();
        }
        if(endDay == null || endDay <= 0) {
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

    public Integer getStartDay() {
        return startDay;
    }
    public Integer getEndDay() {
        return endDay;
    }
}
