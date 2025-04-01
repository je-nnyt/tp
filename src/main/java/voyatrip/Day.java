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
        sb.append("Day Budget: $" + budget + "\n");
        for (int i = 0; i < activities.size(); i++) {
            sb.append("Activity " + (i + 1) + ": " + activities.get(i) + "\n");
        }
        return sb.toString();
    }

    public void addBudget(int budget) {
        this.budget += budget;
    }

    /**
     * This method is used to get the budget of the day.
     * @return The budget of the day.
     */
    public Integer getBudget() {
        return budget;
    }
}
