package voyatrip.command;

import org.junit.jupiter.api.Test;
import voyatrip.command.types.Command;
import voyatrip.command.types.CommandAction;
import voyatrip.command.types.CommandTarget;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ParserTest {
    private static final Parser PARSER = new Parser();

    @Test
    void parse_dummyMODIFYTRIP_successful() {
        assertDoesNotThrow(() -> {
            Command command = PARSER.parse(
                    "modify trip --index 1 --name Vietnam --start 1-5 --end 7-5 --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY);
        });
    }

    @Test
    void parse_dummyMODIFYTRIPWITHOUTINDEX_successful() {
        PARSER.setCurrentTarget(CommandTarget.ITINERARY);
        assertDoesNotThrow(() -> {
            Command command = PARSER.parse("modify trip --name Vietnam --start 1-5 --end 7-5 --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY_TRIP_WITHOUT_INDEX);
        });
    }

    @Test
    void parse_dummyModifyAccommodation_successful() {
        assertDoesNotThrow(() -> {
            PARSER.setCurrentTrip("Vietnam");
            PARSER.setCurrentTarget(CommandTarget.ACCOMMODATION);
            Command command = PARSER.parse("modify accommodation --index 1 --name Hotel --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY);
        });
    }

    @Test
    void parse_dummyModifyTransportation_successful() {
        assertDoesNotThrow(() -> {
            PARSER.setCurrentTrip("Vietnam");
            PARSER.setCurrentTarget(CommandTarget.TRANSPORTATION);
            Command command = PARSER.parse("modify transportation --index 1 --name Flight --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY);
        });
    }

    @Test
    void parse_dummyModifyItinerary_successful() {
        assertDoesNotThrow(() -> {
            PARSER.setCurrentTrip("Vietnam");
            PARSER.setCurrentTarget(CommandTarget.ITINERARY);
            Command command = PARSER.parse(
                    "modify itinerary --name Museum --time 10:00 --day 1 --index 1\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY);
        });
    }
}
