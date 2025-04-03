package voyatrip.command.types;

import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidArgumentKeyword;
import voyatrip.command.exceptions.InvalidArgumentValue;
import voyatrip.command.exceptions.InvalidDateFormat;
import voyatrip.command.exceptions.InvalidNumberFormat;
import voyatrip.command.exceptions.MissingArgument;

public class AccommodationCommand extends Command {
    private String trip;
    private String name;
    private Integer budget;
    private Integer index;
    private Integer startDay;
    private Integer endDay;
    private ArrayList<Integer> days;

    public AccommodationCommand(CommandAction commandAction,
                                CommandTarget commandTarget,
                                String trip,
                                ArrayList<String> arguments)
            throws InvalidArgumentKeyword,
            InvalidDateFormat,
            InvalidArgumentValue,
            InvalidNumberFormat,
            MissingArgument {
        super(commandAction, commandTarget);
        this.trip = trip;
        name = null;
        budget = null;
        index = null;
        startDay = null;
        endDay = null;
        days = null;

        processRawArgument(arguments);
        if (commandAction == CommandAction.ADD) {
            storeDaysInList();
        }
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
    protected void matchArgument(String argument) throws InvalidArgumentKeyword, InvalidNumberFormat {
        String argumentKeyword = argument.split("\\s+")[0];
        String argumentValue = argument.replaceFirst(argumentKeyword, "").strip();
        argumentKeyword = argumentKeyword.toLowerCase();

        try {
            switch (argumentKeyword) {
            case "name", "n" -> name = argumentValue;
            case "budget", "b" -> budget = Integer.parseInt(argumentValue);
            case "index", "i" -> index = Integer.parseInt(argumentValue);
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

        boolean isMissingAddArgument = name == null || budget == null || startDay == null || endDay == null;
        boolean isMissingDeleteArgument = name == null && index == null;
        boolean isMissingModifyArgument = index == null ||
                (name == null && budget == null && startDay == null && endDay == null);

        if (isAdd && isMissingAddArgument ||
                isDelete && isMissingDeleteArgument ||
                isModify && isMissingModifyArgument) {
            throw new MissingArgument();
        }

        if (budget != null && budget < 0) {
            throw new InvalidArgumentValue();
        }
    }

    private void storeDaysInList() {
        days = new ArrayList<>();
        for (int i = startDay; i <= endDay; i++) {
            days.add(i);
        }
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

    public ArrayList<Integer> getDays() {
        return days;
    }
}
