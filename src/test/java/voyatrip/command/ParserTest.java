package voyatrip.command;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import voyatrip.command.types.Command;
import voyatrip.command.types.CommandAction;
import voyatrip.command.types.CommandTarget;
import voyatrip.command.types.TripsCommand;


import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

class ParserTest {
    private static Parser parser;
    @BeforeEach
    void setUp() {
        parser = new Parser();
    }

    @Test
    void parse_addTripCommandWithTarget_successful() {
        assertDoesNotThrow(() -> {
            Command command = parser.parse("add trip --name Vietnam --start 1-5 --end 7-5 --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.ADD);
            assertEquals(command.getCommandTarget(), CommandTarget.TRIP);
            TripsCommand tripsCommand = (voyatrip.command.types.TripsCommand) command;
            assertEquals(tripsCommand.getName(), "Vietnam");
            assertEquals(tripsCommand.getStartDate(), LocalDate.of(LocalDate.now().getYear(), 5, 1));
            assertEquals(tripsCommand.getEndDate(), LocalDate.of(LocalDate.now().getYear(), 5, 7));
            assertEquals(tripsCommand.getTotalBudget(), 1000);
        });
    }

    @Test
    void parse_addTripCommandWithoutTarget_successful() {
        assertDoesNotThrow(() -> {
            Command command = parser.parse("add --name Vietnam --start 1-5 --end 7-5 --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.ADD);
            assertEquals(command.getCommandTarget(), CommandTarget.TRIP);
            TripsCommand tripsCommand = (voyatrip.command.types.TripsCommand) command;
            assertEquals(tripsCommand.getName(), "Vietnam");
            assertEquals(tripsCommand.getStartDate(), LocalDate.of(LocalDate.now().getYear(), 5, 1));
            assertEquals(tripsCommand.getEndDate(), LocalDate.of(LocalDate.now().getYear(), 5, 7));
            assertEquals(tripsCommand.getTotalBudget(), 1000);
        });
    }

    @Test
    void parse_dummyModifyTrip_successful() {
        assertDoesNotThrow(() -> {
            Command command = parser.parse(
                    "modify trip --index 1 --name Vietnam --start 1-5 --end 7-5 --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY);
        });
    }

    @Test
    void parse_dummyModifyTripWithoutIndex_successful() {
        parser.setCurrentTrip("my trip");
        parser.setCurrentTarget(CommandTarget.ITINERARY);
        assertDoesNotThrow(() -> {
            Command command = parser.parse("modify trip --name Vietnam --start 1-5 --end 7-5 --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY_TRIP_WITHOUT_INDEX);
        });
    }

    @Test
    void parse_dummyModifyAccommodation_successful() {
        assertDoesNotThrow(() -> {
            parser.setCurrentTrip("Vietnam");
            parser.setCurrentTarget(CommandTarget.ACCOMMODATION);
            Command command = parser.parse("modify accommodation --index 1 --name Hotel --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY);
        });
    }

    @Test
    void parse_dummyModifyTransportation_successful() {
        assertDoesNotThrow(() -> {
            parser.setCurrentTrip("Vietnam");
            parser.setCurrentTarget(CommandTarget.TRANSPORTATION);
            Command command = parser.parse("modify transportation --index 1 --name Flight --budget 1000\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY);
        });
    }

    @Test
    void parse_dummyModifyItinerary_successful() {
        assertDoesNotThrow(() -> {
            parser.setCurrentTrip("Vietnam");
            parser.setCurrentTarget(CommandTarget.ITINERARY);
            Command command = parser.parse(
                    "modify itinerary --name Museum --time 10:00 --day 1 --index 1\n");
            assertEquals(command.getCommandAction(), CommandAction.MODIFY);
        });
    }
}
