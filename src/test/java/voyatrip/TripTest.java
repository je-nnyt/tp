package voyatrip;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.time.LocalDate;
import java.util.ArrayList;

import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;


import voyatrip.command.exceptions.InvalidCommand;

public class TripTest {

    private Trip trip;

    @BeforeEach
    void setUp() {
        trip = new Trip("Vietnam", LocalDate.of(2025, 6, 10),
                LocalDate.of(2025, 6, 17), 8, 500);
    }

    @Test
    void addTransportation_uniqueTransportationName_success() throws InvalidCommand {
        trip.addTransportation("VietJet Air", "Plane", 200, 2);
        Assertions.assertTrue(trip.isContainsTransportation("VietJet Air"));

    }

    @Test
    void addTransportation_duplicateTransportationName_failure() throws InvalidCommand {
        trip.addTransportation("VietJet Air", "Plane", 200, 1);
        Assertions.assertThrows(InvalidCommand.class, () -> {
            trip.addTransportation("VietJet Air", "Plane", 200, 1);
        });
    }

    @Test
    void deleteTransportationByIndex_indexWithinRange_success() throws InvalidCommand {
        trip.addTransportation("SBS Transit 170", "bus", 5, 1);
        trip.deleteTransportation(1);
        Assertions.assertFalse(trip.isContainsTransportation("SBS Transit 170"));

    }

    @Test
    void deleteTransportationByIndex_indexWithinRange_failure() throws InvalidCommand {
        trip.addTransportation("SBS Transit 170", "bus", 5, 1);
        Assertions.assertThrows(InvalidCommand.class, () -> {
            trip.deleteTransportation(2);
        });

    }

    @Test
    void deleteTransportationByName_nameExists_success() throws InvalidCommand {
        trip.addTransportation("SBS Transit 170", "bus", 5, 1);
        trip.deleteTransportation("SBS Transit 170");
        Assertions.assertFalse(trip.isContainsAccommodation("SBS Transit 170"));
    }

    @Test
    void deleteTransportationByName_nameDoesNotExists_failure() throws InvalidCommand {
        trip.addTransportation("SBS Transit 170", "bus", 5, 1);
        Assertions.assertThrows(InvalidCommand.class, () -> {
            trip.deleteTransportation("    Transit 170");
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
        Assertions.assertThrows(InvalidCommand.class, () ->
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
        Assertions.assertThrows(InvalidCommand.class, () ->
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
        Assertions.assertThrows(InvalidCommand.class, () ->
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
        assertDoesNotThrow(() -> {
            trip.addActivity(8, "Visit the beach", "10:00");
            trip.setEndDate(LocalDate.of(2025, 6, 18));
            trip.updateItinerarySize();
            Assertions.assertEquals(9, trip.getItinerarySize());
        });
    }

    @Test
    void toFromJson_fullMultipleParam_returnSameTrip() {
        Trip testTrip = new Trip("Vietnam", LocalDate.of(2025, 6, 10),
                LocalDate.of(2025, 6, 17), 8, 500);

        ArrayList<Transportation> transportations = new ArrayList<>();
        transportations.add(new Transportation("VietJet Air", "Plane", 200, 2));
        transportations.add(new Transportation("SBS Transit 170", "bus", 5, 1));
        testTrip.setTransportations(transportations);

        ArrayList<Accommodation> testAccommodations = new ArrayList<>();
        testAccommodations.add(new Accommodation("Hotel", 100, new ArrayList<>(java.util.List.of(1, 2, 3))));
        testAccommodations.add(new Accommodation("Park Hyatt Saigon", 800, new ArrayList<>(java.util.List.of(4, 5))));
        testTrip.setAccommodations(testAccommodations);

        ArrayList<Day> testItineraries = new ArrayList<>();
        ArrayList<Activity> testDay1Activities = new ArrayList<>();
        ArrayList<Activity> testDay2Activities = new ArrayList<>();
        testDay1Activities.add(new Activity("Visit the beach", "10:00"));
        testDay1Activities.add(new Activity("Visit the museum", "14:00"));
        testDay2Activities.add(new Activity("Visit the park", "09:00"));
        testDay1Activities.add(new Activity("Visit the zoo", "15:00"));
        Day testDay1 = new Day(100f);
        testDay1.setActivities(testDay1Activities);
        Day testDay2 = new Day(200f);
        testDay2.setActivities(testDay2Activities);
        testTrip.setItineraries(testItineraries);

        JSONObject json = testTrip.toJson();
        Trip newTrip = Trip.fromJson(json);
        Assertions.assertEquals(testTrip, newTrip);
    }
}
