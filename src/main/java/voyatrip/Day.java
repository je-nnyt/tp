package voyatrip;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONObject;

import voyatrip.command.exceptions.ActivityNotFound;
import voyatrip.command.exceptions.DuplicatedName;
import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.InvalidIndex;
import voyatrip.ui.Ui;

public class Day {
    private ArrayList<Activity> activities = new ArrayList<Activity>();
    private Float budget;
    private Logger logger = Logger.getLogger(Day.class.getName());

    public Day(Float budget) {
        this.budget = budget;
    }

    public void addActivity(Activity activity) throws InvalidCommand {
        if (isContain(activity.getName())) {
            logger.log(Level.WARNING, "Activity already exists");
            throw new DuplicatedName();
        }
        activities.add(activity);
    }

    public void deleteActivity(Integer index) throws InvalidCommand {
        try {
            Ui.printDeleteActivityMessage(activities.get(index - 1));
            activities.remove(index - 1);
            logger.log(Level.INFO, "Finished deleting activity");
        } catch (IndexOutOfBoundsException e) {
            logger.log(Level.WARNING, "Index out of bounds");
            throw new InvalidIndex();
        }
    }

    public void deleteActivity(String name) throws InvalidCommand {
        for (Activity activity : activities) {
            if (activity.getName().equals(name)) {
                Ui.printDeleteActivityMessage(activity);
                activities.remove(activity);
                logger.log(Level.INFO, "Finished deleting activity");
                return;
            }
        }
        logger.log(Level.WARNING, "activity not found");
        throw new ActivityNotFound();
    }

    private boolean isContain(String name) {
        for (int i = 0; i < activities.size(); i++) {
            if (activities.get(i).getName().equals(name)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Budget: $").append(budget).append("\n");
        sb.append("Activities: \n");
        if (activities.isEmpty()) {
            sb.append("No activities added yet.\n");
        }
        for (int i = 0; i < activities.size(); i++) {
            sb.append(i + 1).append(": ").append(activities.get(i)).append("\n");
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Day)) {
            return false;
        }

        boolean budgetEquals = this.budget.equals(((Day) other).budget);
        boolean activitiesEquals = true;
        for (Activity activity : activities) {
            if (!((Day) other).activities.contains(activity)) {
                activitiesEquals = false;
                break;
            }
        }

        return budgetEquals && activitiesEquals;
    }

    // the following methods are for JSON serialization

    /**
     * Converts the Day object to a JSON object.
     * @return JSON object representing the Day.
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("budget", budget);

        if (activities != null && !activities.isEmpty()) {
            JSONArray activitiesArray = new JSONArray();
            for (Activity activity : activities) {
                activitiesArray.put(activity.toJson());
            }
            json.put("activities", activitiesArray);
        }

        return json;
    }

    public static Day fromJson(JSONObject json) {
        Float budget = json.getFloat("budget");
        Day day = new Day(budget);

        if (json.has("activities")) {
            JSONArray activitiesArray = json.getJSONArray("activities");
            ArrayList<Activity> activities = new ArrayList<>();
            for (int i = 0; i < activitiesArray.length(); i++) {
                activities.add(Activity.fromJson(activitiesArray.getJSONObject(i)));
            }
            day.setActivities(activities);
        }

        return day;
    }

    public void setActivities(ArrayList<Activity> activities) {
        this.activities = activities;
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
