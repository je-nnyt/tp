package voyatrip;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


import voyatrip.command.exceptions.InvalidCommand;

public class TripTest {

    private Trip trip;

    @BeforeEach
    void setUp() {
        trip = new Trip("Vietnam", LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 17), 7, 500);
    }

    @Test
    void testAddTransportationSuccess() throws InvalidCommand {
        trip.addTransportation("VietJet Air", "Plane", 200);
        Assertions.assertTrue(trip.isContainsTransportation("VietJet Air"));

    }

    @Test
    void testAddTransportationFailure() throws InvalidCommand {
        trip.addTransportation("VietJet Air", "Plane", 200);
        Assertions.assertThrows(InvalidCommand.class, ()->  {
            trip.addTransportation("VietJet Air", "Plane", 200);
        });
    }

    @Test
    void testDeleteTransportationSuccess() throws InvalidCommand {
        trip.addTransportation("SBS Transit 170", "bus", 5);
        trip.deleteTransportation("SBS Transit 170");
        Assertions.assertFalse(trip.isContainsTransportation("SBS Transit 170"));

    }

    @Test
    void testDeleteTransportationFailure() throws InvalidCommand {
        trip.addTransportation("VietJet Air", "Plane", 200);
        trip.deleteTransportation("VietJet Air");

        Assertions.assertThrows(InvalidCommand.class, ()->  {
            trip.deleteTransportation("VietJet Air");
        });

    }
}
