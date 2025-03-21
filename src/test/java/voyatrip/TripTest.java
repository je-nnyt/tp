package voyatrip;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import voyatrip.command.exceptions.InvalidCommand;

class TripTest {

    private Trip trip;

    @BeforeEach
    void setUp() {
        trip = new Trip("Vietnam", LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 17), 7, 500);
    }

    @Test
    void testAddTransportation() throws InvalidCommand {
        trip.addTransportation("VietJet Air", "Plane", 200);
        assertEquals(1, trip.getTransportations().size()); // Transportation list should have 1 item
        assertEquals("VietJet Air", trip.getTransportations().get(0).getName()); //Neme should match
        assertEquals("Plane", trip.getTransportations().get(0).getMode()); //Mode should match
        assertEquals(200, trip.getTransportations().get(0).getBudget()); //Budget should match
    }

    @Test
    void testDeleteTransportation() throws InvalidCommand {
        trip.addTransportation("SBS Transit 170", "bus", 5);
        trip.deleteTransportation(1);
        assertEquals(0, trip.getTransportations().size()); // Transportation list should have 0 item
    }
}
