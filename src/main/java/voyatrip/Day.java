package voyatrip;

import java.util.ArrayList;

public class Day {
    private ArrayList<Activity> activities = new ArrayList<Activity>();
    private Float budget;

    public Day(Float budget) {
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

    /**
     * This method is used to get the budget of the day.
     * @return The budget of the day.
     */
    public Float getBudget() {
        return budget;
    }

    public ArrayList<Activity> getActivities() {
        return activities;
    }
}
