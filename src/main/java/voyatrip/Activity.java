package voyatrip;

import org.json.JSONObject;

public class Activity {
    private String name;
    private String time;

    public Activity(String name, String time) {
        this.name = name;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name + " at " + time;
    }

    // the following methods are for JSON serialization

    /**
     * Converts the Activity object to a JSON object.
     * @return JSON object representing the Activity.
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("time", time);

        return json;
    }

    /**
     * Converts a JSON object to an Activity object.
     * @param json JSON object representing the Activity.
     * @return Activity object.
     */
    public static Activity fromJson(JSONObject json) {
        String name = json.getString("name");
        String time = json.getString("time");

        return new Activity(name, time);
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Activity)) {
            return false;
        }

        boolean nameEquals = this.name.equals(((Activity) other).name);
        boolean timeEquals = this.time.equals(((Activity) other).time);

        return nameEquals && timeEquals;
    }
}
