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
    void testAddTransportation_Success() throws InvalidCommand {
        trip.addTransportation("VietJet Air", "Plane", 200);
        Assertions.assertTrue(trip.isContainsTransportation("VietJet Air"));

    }

    @Test
    void testAddTransportation_Failure() throws InvalidCommand {
        trip.addTransportation("VietJet Air", "Plane", 200);
        Assertions.assertThrows(InvalidCommand.class, ()->  {
            trip.addTransportation("VietJet Air", "Plane", 200);
        });
    }

    @Test
    void testDeleteTransportation_Success() throws InvalidCommand {
        trip.addTransportation("SBS Transit 170", "bus", 5);
        trip.deleteTransportation("SBS Transit 170");
        Assertions.assertFalse(trip.isContainsTransportation("SBS Transit 170"));

    }

    @Test
    void testDeleteTransportation_Failure() throws InvalidCommand {
        trip.addTransportation("VietJet Air", "Plane", 200);
        trip.deleteTransportation("VietJet Air");

        Assertions.assertThrows(InvalidCommand.class, ()->  {
            trip.deleteTransportation("VietJet Air");
        });

    }
}
