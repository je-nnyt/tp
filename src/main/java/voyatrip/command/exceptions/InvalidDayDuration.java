package voyatrip.command.exceptions;

import java.io.IOException;

public class InvalidDayDuration extends IOException {
    public InvalidDayDuration() {
        super("Invalid day entered.");
    }

    public InvalidDayDuration(String message) {
        super(message);
    }
}
