package voyatrip.command.types;

import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidCommand;

public abstract class Command {
    protected CommandAction commandAction;
    protected CommandTarget commandTarget;

    public Command(CommandAction commandAction, CommandTarget commandTarget) {
        this.commandAction = commandAction;
        this.commandTarget = commandTarget;
    }

    protected void processRawArgument(ArrayList<String> arguments) throws InvalidCommand {
        for (String argument : arguments) {
            matchArgument(argument);
        }

        if (isInvalidCommand()) {
            throw new InvalidCommand();
        }
    }

    protected abstract void matchArgument(String argument) throws InvalidCommand;

    protected abstract boolean isInvalidCommand();

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
