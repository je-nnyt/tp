package voyatrip.command.types;

public class ExitCommand extends Command {
    public ExitCommand() {
        super(CommandAction.EXIT, CommandTarget.TRIP);
    }

    @Override
    protected void matchArgument(String argument) {
        // No argument to be matched
    }

    @Override
    protected boolean isMissingArgument() {
        // No argument
        return false;
    }
}
