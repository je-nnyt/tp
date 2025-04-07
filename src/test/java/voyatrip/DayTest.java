package voyatrip;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.json.JSONObject;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import voyatrip.logic.command.exceptions.ActivityNotFound;
import voyatrip.logic.command.exceptions.DuplicatedName;
import voyatrip.logic.command.exceptions.InvalidIndex;
import voyatrip.model.Activity;
import voyatrip.model.Day;

import java.util.ArrayList;

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
        assertThrowsExactly(DuplicatedName.class, () ->
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
        assertThrowsExactly(ActivityNotFound.class, () -> day.deleteActivity("Visit nothing"));
    }

    @Test
    void equals_differentObjectWithActivity_success() {
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(new Activity("Visit the beach", "10:00"));

        Day day1 = new Day(100.0f);
        Day day2 = new Day(100.0f);

        day1.setActivities(activities);
        day2.setActivities(activities);

        assertEquals(day1, day2);
    }

    @Test
    void equals_differentObjectWithoutActivity_success() {
        Day day1 = new Day(100.0f);
        Day day2 = new Day(100.0f);

        assertEquals(day1, day2);
    }

    @Test
    void toFromJson_withMultipleActivity_returnSameDayObject() {
        Day day = new Day(100.0f);
        ArrayList<Activity> activities = new ArrayList<>();
        activities.add(new Activity("Visit the beach", "10:00"));
        activities.add(new Activity("Visit the restaurant", "12:00"));
        day.setActivities(activities);

        JSONObject json = day.toJson();
        Day dayFromJson = Day.fromJson(json);

        assertEquals(day, dayFromJson);
    }
}
