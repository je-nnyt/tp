package voyatrip;

import java.time.LocalDate;
import java.util.ArrayList;

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
    void addAccommodation_uniqueAccommodationName_success() throws InvalidCommand {
        ArrayList<Integer> days = new ArrayList<>();
        days.add(3);
        days.add(4);
        trip.addAccommodation("Park Hyatt Saigon", 800, days);
        Assertions.assertTrue(trip.isContainsAccommodation("Park Hyatt Saigon"));
    }

    @Test
    void addAccommodation_duplicateAccommodationName_failure() throws InvalidCommand {
        ArrayList<Integer> days = new ArrayList<>();
        days.add(5);
        days.add(6);
        trip.addAccommodation("Park Hyatt Saigon", 800, days);
        Assertions.assertThrows(InvalidCommand.class, ()->
                trip.addAccommodation("Park Hyatt Saigon", 600, days));
    }

    @Test
    void deleteAccommodationByIndex_indexWithinRange_success() throws InvalidCommand {
        ArrayList<Integer> days = new ArrayList<>();
        days.add(1);
        days.add(2);
        trip.addAccommodation("Park Hyatt Saigon", 800, days);
        trip.deleteAccommodation(1);
        Assertions.assertFalse(trip.isContainsAccommodation("Park Hyatt Saigon"));
    }

    @Test
    void deleteAccommodationByIndex_indexOutOfBounds_failure() throws InvalidCommand {
        ArrayList<Integer> days = new ArrayList<>();
        days.add(3);
        days.add(4);
        trip.addAccommodation("Park Hyatt Saigon", 800, days);
        Assertions.assertThrows(InvalidCommand.class, ()->
                trip.deleteAccommodation(2));
    }

    @Test
    void deleteAccommodationByName_nameExists_success() throws InvalidCommand {
        ArrayList<Integer> days = new ArrayList<>();
        days.add(5);
        days.add(6);
        trip.addAccommodation("Park Hyatt Saigon", 800, days);
        trip.deleteAccommodation("Park Hyatt Saigon");
        Assertions.assertFalse(trip.isContainsAccommodation("Park Hyatt Saigon"));
    }

    @Test
    void deleteAccommodationByName_nameDoesntExist_failure() throws InvalidCommand {
        ArrayList<Integer> days = new ArrayList<>();
        days.add(1);
        days.add(2);
        trip.addAccommodation("Park Hyatt Saigon", 800, days);
        Assertions.assertThrows(InvalidCommand.class, ()->
                trip.deleteAccommodation("Lotte Hotel Saigon"));
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
