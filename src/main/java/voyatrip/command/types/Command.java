package voyatrip.command.types;

import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidArgumentKeyword;
import voyatrip.command.exceptions.InvalidArgumentValue;
import voyatrip.command.exceptions.InvalidDate;
import voyatrip.command.exceptions.InvalidNumberFormat;
import voyatrip.command.exceptions.MissingArgument;

public abstract class Command {
    protected CommandAction commandAction;
    protected CommandTarget commandTarget;

    public Command(CommandAction commandAction, CommandTarget commandTarget) {
        this.commandAction = commandAction;
        this.commandTarget = commandTarget;
    }

    /**
     * Match the arguments with the keywords and put them into the corresponding fields.
     * Throws exceptions if the arguments are invalid.
     *
     * @param arguments List of arguments.
     * @throws InvalidArgumentKeyword If the argument keyword is invalid.
     * @throws InvalidArgumentValue If the argument value is invalid.
     * @throws InvalidDateFormat If the date format is invalid.
     * @throws InvalidNumberFormat If the number format is invalid.
     * @throws MissingArgument If there is missing argument.
     */
    protected void processRawArgument(ArrayList<String> arguments)
            throws InvalidArgumentKeyword,
            InvalidArgumentValue,
            InvalidDate,
            InvalidNumberFormat,
            MissingArgument {
        for (String argument : arguments) {
            matchArgument(argument);
        }

        validateArgument();
    }

    protected abstract void matchArgument(String argument)
            throws InvalidArgumentKeyword, InvalidDate, InvalidNumberFormat, InvalidArgumentValue, MissingArgument;

    protected abstract void validateArgument()
            throws InvalidArgumentValue, MissingArgument;

    public CommandAction getCommandAction() {
        return commandAction;
    }

    public CommandTarget getCommandTarget() {
        return commandTarget;
    }

    protected void setCommandAction(CommandAction commandAction) {
        this.commandAction = commandAction;
    }
}
