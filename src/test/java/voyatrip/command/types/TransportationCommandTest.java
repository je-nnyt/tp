package voyatrip.command.types;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import voyatrip.logic.command.exceptions.MissingArgument;
import voyatrip.logic.command.types.CommandAction;
import voyatrip.logic.command.types.CommandTarget;
import voyatrip.logic.command.types.TransportationCommand;

public class TransportationCommandTest {
    @Test
    void listAllTransportationCommand_successful() {
        assertDoesNotThrow(() -> {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("all");
            TransportationCommand command = new
                    TransportationCommand(CommandAction.LIST, CommandTarget.TRANSPORTATION, "trip name", arguments);
            assertEquals(CommandAction.LIST_BY_NAME, command.getCommandAction());
            assertEquals(CommandTarget.TRANSPORTATION, command.getCommandTarget());
            assertEquals("all", command.getName());
        });
    }

    @Test
    void listTransportationCommandByName_successful() {
        assertDoesNotThrow(() -> {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("n name");
            TransportationCommand command = new
                    TransportationCommand(CommandAction.LIST, CommandTarget.TRANSPORTATION, "trip name", arguments);
            assertEquals(CommandAction.LIST_BY_NAME, command.getCommandAction());
            assertEquals(CommandTarget.TRANSPORTATION, command.getCommandTarget());
            assertEquals("name", command.getName());
        });
    }

    @Test
    void listTransportationCommandByIndex_successful() {
        assertDoesNotThrow(() -> {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("i 1");
            TransportationCommand command = new
                    TransportationCommand(CommandAction.LIST, CommandTarget.TRANSPORTATION, "trip name", arguments);
            assertEquals(CommandAction.LIST_BY_INDEX, command.getCommandAction());
            assertEquals(CommandTarget.TRANSPORTATION, command.getCommandTarget());
            assertEquals(1, command.getIndex());
        });
    }

    @Test
    void listTransportationIncorrectArgument_failure() {
        assertThrowsExactly(MissingArgument.class, () -> {
            ArrayList<String> arguments = new ArrayList<>();
            arguments.add("b 100");
            new TransportationCommand(CommandAction.LIST, CommandTarget.TRANSPORTATION, "trip", arguments);
        });
    }
}
