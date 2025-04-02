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
        trip = new Trip("Vietnam", LocalDate.of(2025, 6, 10), LocalDate.of(2025, 6, 17), 8, 500);
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

    @Test
    void updateItinerarySize_smallerSize_success() {
        trip.setEndDate(LocalDate.of(2025, 6, 16));
        trip.updateItinerarySize();
        Assertions.assertEquals(7, trip.getItinerarySize());
    }

    @Test
    void updateItinerarySize_largerSize_success() {
        trip.addActivity(8, "Visit the beach", "10:00");
        trip.setEndDate(LocalDate.of(2025, 6, 18));
        trip.updateItinerarySize();
        Assertions.assertEquals(9, trip.getItinerarySize());
    }
}
