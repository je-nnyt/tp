package voyatrip.command.types;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import voyatrip.command.exceptions.MissingArgument;

public class AccommodationCommandTest {
    @Test
    public void listAllAccommodations_successful() {
        assertDoesNotThrow(() -> {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("all");
            AccommodationCommand command =
                    new AccommodationCommand(CommandAction.LIST, CommandTarget.ACCOMMODATION, "trip", arguments);
            assertEquals(CommandAction.LIST_BY_NAME, command.getCommandAction());
            assertEquals(CommandTarget.ACCOMMODATION, command.getCommandTarget());
            assertEquals("all", command.getName());
        });
    }

    @Test
    public void listAccommodationByIndex_successful() {
        assertDoesNotThrow(() -> {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("i 1");
            AccommodationCommand command =
                    new AccommodationCommand(CommandAction.LIST, CommandTarget.ACCOMMODATION, "trip", arguments);
            assertEquals(CommandAction.LIST_BY_INDEX, command.getCommandAction());
            assertEquals(CommandTarget.ACCOMMODATION, command.getCommandTarget());
            assertEquals(1, command.getIndex());
        });
    }

    @Test
    public void listAccommodationByName_successful() {
        assertDoesNotThrow(() -> {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("n name");
            AccommodationCommand command =
                    new AccommodationCommand(CommandAction.LIST, CommandTarget.ACCOMMODATION, "trip", arguments);
            assertEquals(CommandAction.LIST_BY_NAME, command.getCommandAction());
            assertEquals(CommandTarget.ACCOMMODATION, command.getCommandTarget());
            assertEquals("name", command.getName());
        });
    }

    @Test
    public void listAccommodationIncorrectArgument_failure() {
        assertThrowsExactly(MissingArgument.class, () -> {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("b 100");
            new AccommodationCommand(CommandAction.LIST, CommandTarget.ACCOMMODATION, "trip", arguments);
        });
    }
}
