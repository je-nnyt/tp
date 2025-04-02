package voyatrip;

import java.util.ArrayList;

public class Day {
    private ArrayList<Activity> activities = new ArrayList<Activity>();
    private Integer budget;

    public Day(Integer budget) {
        this.budget = budget;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Budget: $").append(budget).append("\n");
        for (int i = 0; i < activities.size(); i++) {
            sb.append("Activity ").append(i + 1).append(": ").append(activities.get(i)).append("\n");
        }
        return sb.toString();
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }
}
