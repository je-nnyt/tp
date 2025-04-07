package voyatrip.logic.command.types;

import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.time.LocalTime;

import voyatrip.logic.command.exceptions.InvalidArgumentKeyword;
import voyatrip.logic.command.exceptions.InvalidArgumentValue;
import voyatrip.logic.command.exceptions.InvalidDate;
import voyatrip.logic.command.exceptions.InvalidNumberFormat;
import voyatrip.logic.command.exceptions.InvalidTimeFormat;
import voyatrip.logic.command.exceptions.MissingArgument;

public class ItineraryCommand extends Command {
    private String trip;
    private String name;
    private LocalTime time;
    private Integer day;
    private Integer index;

    public ItineraryCommand(CommandAction commandAction,
                            CommandTarget commandTarget,
                            String trip,
                            ArrayList<String> arguments)
            throws InvalidArgumentKeyword,
            InvalidDate,
            InvalidArgumentValue,
            InvalidNumberFormat,
            MissingArgument, InvalidTimeFormat {
        super(commandAction, commandTarget);
        this.trip = trip;
        name = null;
        time = null;
        day = null;
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

        if (commandAction == CommandAction.DELETE_BY_INDEX && name != null) {
            super.setCommandAction(CommandAction.DELETE_BY_NAME);
        }
    }

    @Override
    protected void matchArgument(String argument)
            throws InvalidArgumentKeyword, InvalidArgumentValue, InvalidTimeFormat {

        String argumentKeyword = argument.split("\\s+")[0];
        String argumentValue = argument.replaceFirst(argumentKeyword, "").strip();
        argumentKeyword = argumentKeyword.toLowerCase();

        if (argumentKeyword.isEmpty()) {
            throw new InvalidArgumentKeyword();
        }
        if (argumentValue.isEmpty()) {
            throw new InvalidArgumentValue();
        }

        try {
            switch (argumentKeyword) {
            case "name", "n" -> name = argumentValue;
            case "time", "t" -> time = LocalTime.parse(argumentValue, DateTimeFormatter.ofPattern("H:mm"));
            case "day", "d" -> day = Integer.parseInt(argumentValue);
            case "index", "i" -> index = Integer.parseInt(argumentValue);
            default -> throw new InvalidArgumentKeyword();
            }
        } catch (NumberFormatException e) {
            throw new InvalidNumberFormat();
        } catch (DateTimeParseException e) {
            throw new InvalidTimeFormat();
        }
    }

    @Override
    protected void validateArgument() throws MissingArgument {
        boolean isAdd = commandAction == CommandAction.ADD;
        boolean isDelete = commandAction == CommandAction.DELETE_BY_INDEX ||
                commandAction == CommandAction.DELETE_BY_NAME;
        boolean isModify = commandAction == CommandAction.MODIFY;

        boolean isMissingAddArgument = name == null || time == null || day == null;
        boolean isMissingDeleteArgument = name == null && index == null || day == null;
        boolean isMissingModifyArgument = index == null || day == null || (name == null && time == null);

        if (isAdd && isMissingAddArgument ||
                isDelete && isMissingDeleteArgument ||
                isModify && isMissingModifyArgument) {
            throw new MissingArgument();
        }
    }

    public String getTrip() {
        return trip;
    }

    public String getName() {
        return name;
    }

    public String getTime() {
        return time.format(DateTimeFormatter.ofPattern("HH:mm"));
    }

    public Integer getDay() {
        return day;
    }

    public Integer getIndex() {
        return index;
    }
}
