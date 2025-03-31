package voyatrip.command.types;

import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidArgumentKeyword;
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
            throws InvalidArgumentKeyword, InvalidNumberFormat, InvalidDateFormat, MissingArgument {
        for (String argument : arguments) {
            matchArgument(argument);
        }

        if (isMissingArgument()) {
            throw new MissingArgument();
        }
    }

    protected abstract void matchArgument(String argument)
            throws InvalidArgumentKeyword, InvalidNumberFormat, InvalidDateFormat;

    protected abstract boolean isMissingArgument();

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
