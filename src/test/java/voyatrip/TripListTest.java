package voyatrip;

import org.junit.jupiter.api.Test;
import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.TripNotFoundException;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

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
        } catch (InvalidCommand | TripNotFoundException e) {
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
        } catch (InvalidCommand | TripNotFoundException e) {
            fail("exception should not have been thrown");
        }

        assertThrows(TripNotFoundException.class, () -> testTripList.delete("Test Trip"));
    }
}