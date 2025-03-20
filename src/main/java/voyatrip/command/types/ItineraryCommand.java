package voyatrip.command.types;

import voyatrip.command.exceptions.InvalidCommand;

public class ItineraryCommand extends Command {
    private String trip;
    private String name;
    private String time;
    private Integer day;
    private Integer index;

    public ItineraryCommand(CommandAction commandAction,
                            CommandTarget commandTarget,
                            String trip,
                            String rawArgument) throws InvalidCommand {
        super(commandAction, commandTarget);
        this.trip = trip;
        name = null;
        time = null;
        day = null;
        index = null;

        processRawArgument(rawArgument);
    }

    @Override
    protected void processRawArgument(String rawArgument) throws InvalidCommand {
        super.processRawArgument(rawArgument);

        if (commandAction == CommandAction.DELETE_BY_INDEX && name != null) {
            super.setCommandAction(CommandAction.DELETE_BY_NAME);
        }
    }

    @Override
    protected void matchArgument(String argument) throws InvalidCommand {
        String argumentKeyword = argument.split("\\s+")[0];
        String argumentValue = argument.replaceFirst(argumentKeyword, "").strip();
        argumentKeyword = argumentKeyword.toLowerCase();

        try {
            switch (argumentKeyword) {
            case "name", "n" -> name = argumentValue;
            case "time", "t" -> time = argumentValue;
            case "day", "d" -> day = Integer.parseInt(argumentValue);
            case "index", "i" -> index = Integer.parseInt(argumentValue);
            default -> throw new InvalidCommand();
            }
        } catch (NumberFormatException e) {
            throw new InvalidCommand();
        }
    }

    @Override
    protected boolean isInvalidCommand() {
        boolean isInvalidName = name == null;
        boolean isInvalidAdd = isInvalidName || time == null || day == null;
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

    public String getTime() {
        return time;
    }

    public Integer getDay() {
        return day;
    }

    public Integer getIndex() {
        return index;
    }
}
