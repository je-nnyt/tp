package voyatrip.command.types;

import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidArgumentKeyword;
import voyatrip.command.exceptions.InvalidArgumentValue;
import voyatrip.command.exceptions.InvalidDateFormat;
import voyatrip.command.exceptions.InvalidNumberFormat;
import voyatrip.command.exceptions.MissingArgument;

public abstract class Command {
    protected CommandAction commandAction;
    protected CommandTarget commandTarget;

    public Command(CommandAction commandAction, CommandTarget commandTarget) {
        this.commandAction = commandAction;
        this.commandTarget = commandTarget;
    }

    protected void processRawArgument(ArrayList<String> arguments)
            throws InvalidArgumentKeyword,
            InvalidArgumentValue,
            InvalidDateFormat,
            InvalidNumberFormat,
            MissingArgument {
        for (String argument : arguments) {
            matchArgument(argument);
        }

        validateArgument();
    }

    protected abstract void matchArgument(String argument)
            throws InvalidArgumentKeyword, InvalidDateFormat, InvalidNumberFormat, InvalidArgumentValue;

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
