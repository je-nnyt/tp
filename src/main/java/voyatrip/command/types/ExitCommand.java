package voyatrip.command.types;

import voyatrip.command.exceptions.InvalidCommand;

public class ExitCommand extends Command {
    public ExitCommand() {
        super(CommandAction.EXIT, CommandTarget.TRIP);
    }

    @Override
    protected void matchArgument(String argument) throws InvalidCommand {
        // No argument to be matched
    }

    @Override
    protected boolean isInvalidCommand() {
        return false;
    }
}
