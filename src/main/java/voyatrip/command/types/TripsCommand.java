package voyatrip.command.types;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Arrays;

import voyatrip.command.exceptions.InvalidArgumentKeyword;
import voyatrip.command.exceptions.InvalidArgumentValue;
import voyatrip.command.exceptions.InvalidBudget;
import voyatrip.command.exceptions.InvalidDate;
import voyatrip.command.exceptions.InvalidName;
import voyatrip.command.exceptions.InvalidNumberFormat;
import voyatrip.command.exceptions.InvalidTimeFormat;
import voyatrip.command.exceptions.MissingArgument;

public class TripsCommand extends Command {
    static final String[] INVALID_NAMES = {"root", "all"};
    static final Integer MAX_NUM_DAYS = 366;

    private String name;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer numDay;
    private Integer totalBudget;
    private Integer index;

    public TripsCommand(CommandAction commandAction,
                        CommandTarget commandTarget,
                        ArrayList<String> arguments)
            throws InvalidArgumentKeyword,
            InvalidArgumentValue,
            InvalidDate,
            InvalidNumberFormat,
            MissingArgument, InvalidTimeFormat {
        super(commandAction, commandTarget);
        name = null;
        startDate = null;
        endDate = null;
        numDay = null;
        totalBudget = null;
        index = null;

        processRawArgument(arguments);
    }

    @Override
    protected void processRawArgument(ArrayList<String> arguments)
            throws InvalidArgumentKeyword,
            InvalidArgumentValue,
            InvalidDate,
            InvalidNumberFormat,
            MissingArgument, InvalidTimeFormat {
        super.processRawArgument(arguments);

        calculateNumDay();
        validateNumDay();

        processCommandVariation();
    }

    private void calculateNumDay() {
        if (startDate != null) {
            numDay = Math.toIntExact(ChronoUnit.DAYS.between(startDate, endDate) + 1);
        }
    }

    /**
     * Validates the number of days in the trip is within the valid range.
     * @throws InvalidArgumentValue if the number of days is greater than 365.
     */
    private void validateNumDay() throws InvalidArgumentValue {
        if (numDay != null && numDay > MAX_NUM_DAYS) {
            throw new InvalidArgumentValue();
        }
    }

    private void processCommandVariation() {
        if (commandAction == CommandAction.DELETE_BY_INDEX && name != null) {
            super.setCommandAction(CommandAction.DELETE_BY_NAME);
        } else if (commandAction == CommandAction.CHANGE_DIRECTORY) {
            if (name != null) {
                super.setCommandAction(CommandAction.CHANGE_TRIP_BY_NAME);
            } else if (index != null) {
                super.setCommandAction(CommandAction.CHANGE_TRIP_BY_INDEX);
            } else {
                super.setCommandAction(CommandAction.CHANGE_TRIP_BY_NAME);
                name = "root";
            }
        } else if (commandAction == CommandAction.LIST) {
            if (name != null) {
                super.setCommandAction(CommandAction.LIST_BY_NAME);
            } else if (index != null) {
                super.setCommandAction(CommandAction.LIST_BY_INDEX);
            }
        } else if (commandAction == CommandAction.MODIFY) {
            if (index == null) {
                super.setCommandAction(CommandAction.MODIFY_TRIP_WITHOUT_INDEX);
            }
        }
    }

    @Override
    protected void matchArgument(String argument)
            throws InvalidArgumentKeyword, InvalidNumberFormat, InvalidDate, MissingArgument {
        String argumentKeyword = argument.split("\\s+")[0];
        String argumentValue = argument.replaceFirst(argumentKeyword, "").strip();
        argumentKeyword = argumentKeyword.toLowerCase();

        if (argumentKeyword.isEmpty()) {
            throw new InvalidArgumentKeyword();
        }
        if (!argumentKeyword.equals("all") && argumentValue.isEmpty()) {
            throw new MissingArgument();
        }

        try {
            switch (argumentKeyword) {
            case "name", "n" -> name = argumentValue;
            case "start", "s" -> startDate = parseDate(argumentValue);
            case "end", "e" -> endDate = parseDate(argumentValue);
            case "day", "d" -> numDay = Integer.parseInt(argumentValue);
            case "budget", "b" -> totalBudget = Integer.parseInt(argumentValue);
            case "index", "i" -> index = Integer.parseInt(argumentValue);
            case "all" -> name = "all";
            default -> throw new InvalidArgumentKeyword();
            }
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormat();
        }
    }

    private LocalDate parseDate(String date) throws InvalidDate {
        try {
            if (date.split("-").length == 2) {
                date = date + "-" + LocalDate.now().getYear();
            }
            return LocalDate.parse(date, DateTimeFormatter.ofPattern("d-M-yyyy"));
        } catch (DateTimeParseException e) {
            throw new InvalidDate();
        }
    }

    @Override
    protected void validateArgument() throws InvalidArgumentValue, MissingArgument {
        boolean isAdd = commandAction == CommandAction.ADD;
        boolean isDelete = commandAction == CommandAction.DELETE_BY_INDEX ||
                commandAction == CommandAction.DELETE_BY_NAME;
        boolean isModify = commandAction == CommandAction.MODIFY;
        boolean isModifyWithoutIndex = commandAction == CommandAction.MODIFY_TRIP_WITHOUT_INDEX;
        boolean isList = commandAction == CommandAction.LIST;

        boolean isMissingAddArgument = name == null || startDate == null || endDate == null || totalBudget == null;
        boolean isMissingDeleteArgument = name == null && index == null;
        boolean isMissingModifyArgument = index == null ||
                (name == null && startDate == null && endDate == null && totalBudget == null);
        boolean isMissingModifyWithoutIndexArgument =
                name == null && startDate == null && endDate == null && totalBudget == null;
        boolean isMissingListArgument = name == null && index == null;

        if (isAdd && isMissingAddArgument ||
                isDelete && isMissingDeleteArgument ||
                isModify && isMissingModifyArgument ||
                isModifyWithoutIndex && isMissingModifyWithoutIndexArgument ||
                isList && isMissingListArgument) {
            throw new MissingArgument();
        }

        boolean isInvalidBudget = totalBudget != null && totalBudget < 0;
        boolean isInvalidName = !isList && name != null && Arrays.asList(INVALID_NAMES).contains(name);
        boolean isInvalidDate = startDate != null && endDate != null && startDate.isAfter(endDate);

        if (isInvalidBudget) {
            throw new InvalidBudget();
        }
        if (isInvalidName) {
            throw new InvalidName();
        }
        if (isInvalidDate) {
            throw new InvalidDate();
        }
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public String getName() {
        return name;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public Integer getNumDay() {
        return numDay;
    }

    public Integer getTotalBudget() {
        return totalBudget;
    }

    public Integer getIndex() {
        return index;
    }
}
