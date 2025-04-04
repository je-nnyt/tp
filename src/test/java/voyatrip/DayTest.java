package voyatrip;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import voyatrip.command.exceptions.InvalidArgumentValue;
import voyatrip.command.exceptions.InvalidIndex;

public class DayTest {

    private Day day;

    @BeforeEach
    void setUp() {
        day = new Day(100.0f);
        assertDoesNotThrow(() -> day.addActivity(new Activity("Visit the beach", "10:00")));
        assertDoesNotThrow(() -> day.addActivity(new Activity("Visit the restaurant", "12:00")));
    }

    @Test
    void addActivitySameName_failure() {
        assertThrowsExactly(InvalidArgumentValue.class, () ->
                day.addActivity(new Activity("Visit the beach", "10:00")));
    }

    @Test
    void deleteActivityByIndex_success() {
        assertDoesNotThrow(() -> {
            day.deleteActivity(1);
            Assertions.assertEquals("Visit the restaurant", day.getActivities().get(0).getName());
        });
    }

    @Test
    void deleteActivityByIndex_failure() {
        assertThrowsExactly(InvalidIndex.class, () -> day.deleteActivity(3));
    }

    @Test
    void deleteActivityByName_success() {
        assertDoesNotThrow(() -> {
            day.deleteActivity("Visit the restaurant");
            Assertions.assertEquals("Visit the beach", day.getActivities().get(0).getName());
        });
    }

    @Test
    void deleteActivityByName_failure() {
        assertThrowsExactly(InvalidArgumentValue.class, () -> day.deleteActivity("Visit nothing"));
    }
}
