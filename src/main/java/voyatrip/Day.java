package voyatrip;

import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidCommand;
import voyatrip.command.exceptions.TripNotFoundException;
import voyatrip.ui.Ui;

public class Day {
    private ArrayList<Activity> activities = new ArrayList<Activity>();
    private Integer budget;

    public Day(Integer budget) {
        this.budget = budget;
    }

    public void addActivity(Activity activity) {
        activities.add(activity);
    }

    public void deleteActivity(Integer index) throws InvalidCommand {
        try {
            Ui.printDeleteActivityMessage(activities.get(index - 1));
            activities.remove(index - 1);
        } catch (IndexOutOfBoundsException e) {
            throw new InvalidCommand();
        }
    }

    public void deleteActivity(String name) throws TripNotFoundException {
        for (int i = 0; i < activities.size(); i++) {
            Activity activity = activities.get(i);
            if (activity.getName().equals(name)) {
                activities.remove(activity);
                Ui.printDeleteActivityMessage(activity);
                return;
            }
        }
        throw new TripNotFoundException();
    }

    public boolean isContain(String name) {
        for (Activity activity : activities) {
            if (activity.getName().equals(name)) {
                return true;
            }
        }
        return false;
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
}
