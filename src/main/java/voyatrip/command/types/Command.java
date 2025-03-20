package voyatrip.command.types;

import voyatrip.command.exceptions.InvalidCommand;

public abstract class Command {
    protected CommandAction commandAction;
    protected CommandTarget commandTarget;

    public Command(CommandAction commandAction, CommandTarget commandTarget) {
        this.commandAction = commandAction;
        this.commandTarget = commandTarget;
    }

    protected String[] splitByDoubleHyphen(String command) {
        command = command.strip();
        return command.split("(^--|\\s+--)(?=\\w+\\s+\\w+)");
    }

    protected void processRawArgument(String rawArgument) throws InvalidCommand {
        String[] hyphenSeparatedTokens = splitByDoubleHyphen(rawArgument);
        for (String argument : hyphenSeparatedTokens) {
            if (!argument.isEmpty()) {
                matchArgument(argument);
            }
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
