package voyatrip;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import voyatrip.model.Activity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class ActivityTest {
    @Test
    public void equals_differentObject_success() {
        Activity activity1 = new Activity("Test", "2021-01-01");
        Activity activity2 = new Activity("Test", "2021-01-01");
        assertEquals(activity1, activity2);
    }

    @Test
    public void equals_differentName_success() {
        Activity activity1 = new Activity("Test", "2021-01-01");
        Activity activity2 = new Activity("Test2", "2021-01-01");
        assertNotEquals(activity1, activity2);
    }

    @Test
    public void toFromJson_withNameAndTime_returnSameActivityObject() {
        Activity activity = new Activity("Test", "2021-01-01");
        JSONObject json = activity.toJson();
        Activity activityFromJson = Activity.fromJson(json);
        assertEquals(activity, activityFromJson);
    }
}
