package voyatrip;

import org.junit.jupiter.api.Test;
import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.TripNotFoundException;

import java.time.LocalDate;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;
import static org.junit.jupiter.api.Assertions.assertThrows;

class TripListTest {
    @Test
    public void add_newTrip_success() {
        TripList testTripList = new TripList();
        LocalDate startDate = LocalDate.parse("2021-01-01");
        LocalDate endDate = LocalDate.parse("2021-01-03");
        try {
            testTripList.add("Test Trip", startDate, endDate, 3, 300);
            assertEquals(testTripList.get(1), new Trip("Test Trip", startDate, endDate, 3, 300));
        } catch (InvalidCommand e) {
            fail("exception should not have been thrown");
        }
    }

    @Test
    public void add_newTripFail_throwInvalidCommand() {
        TripList testTripList = new TripList();
        LocalDate startDate = LocalDate.parse("2021-01-01");
        LocalDate endDate = LocalDate.parse("2021-01-03");
        try {
            testTripList.listTrip(1);
            testTripList.add("Test Trip", startDate, endDate, 3, 300);
        } catch (InvalidCommand e) {
            fail("exception should not have been thrown");
        }

        assertThrows(InvalidCommand.class, () -> testTripList.add("Test Trip", startDate, endDate, 3, 300));
    }

    @Test
    public void delete_tripByIndex_successful() {
        TripList testTripList = new TripList();
        LocalDate startDate = LocalDate.parse("2021-01-01");
        LocalDate endDate = LocalDate.parse("2021-01-03");
        try {
            testTripList.add("Test Trip", startDate, endDate, 3, 300);
            testTripList.delete(1);
            assertEquals(testTripList.size(), 0);
        } catch (InvalidCommand e) {
            fail("exception should not have been thrown");
        }
    }

    @Test
    public void delete_tripByIndexFail_throwInvalidCommand() {
        TripList testTripList = new TripList();
        LocalDate startDate = LocalDate.parse("2021-01-01");
        LocalDate endDate = LocalDate.parse("2021-01-03");
        try {
            testTripList.listTrip(1);
            testTripList.add("Test Trip", startDate, endDate, 3, 300);
            testTripList.delete(1);
        } catch (InvalidCommand e) {
            fail("exception should not have been thrown");
        }

        assertThrows(InvalidCommand.class, () -> testTripList.delete(1));
    }

    @Test
    public void delete_tripByName_successful() {
        TripList testTripList = new TripList();
        LocalDate startDate = LocalDate.parse("2021-01-01");
        LocalDate endDate = LocalDate.parse("2021-01-03");
        try {
            testTripList.add("Test Trip", startDate, endDate, 3, 300);
            testTripList.delete("Test Trip");
            assertEquals(testTripList.size(), 0);
        } catch (TripNotFoundException e) {
            fail("exception should not have been thrown");
        } catch (InvalidCommand e) {
            fail("exception should not have been thrown");
        }
    }

    @Test
    public void delete_tripByNameFail_throwTripNotFoundException() {
        TripList testTripList = new TripList();
        LocalDate startDate = LocalDate.parse("2021-01-01");
        LocalDate endDate = LocalDate.parse("2021-01-03");
        try {
            testTripList.listTrip(1);
            testTripList.add("Test Trip", startDate, endDate, 3, 300);
            testTripList.delete("Test Trip");
        } catch (TripNotFoundException e) {
            fail("exception should not have been thrown");
        } catch (InvalidCommand e) {
            fail("exception should not have been thrown");
        }

        assertThrows(TripNotFoundException.class, () -> testTripList.delete("Test Trip"));
    }


    @Test
    public void toFromJson_fullMultipleParm_success() {
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

        TripList testTripList = new TripList();
        ArrayList<Trip> testTrips = new ArrayList<>();
        testTrips.add(testTrip);
        testTrips.add(testTrip);
        testTripList.setTrips(testTrips);

        String jsonStr = testTripList.toJson().get().toString(4);
        TripList newTripList = TripList.fromJson(jsonStr);
        assertEquals(testTripList, newTripList);
    }
}
