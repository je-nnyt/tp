package voyatrip;

import java.util.logging.Level;
import java.util.logging.Logger;

import java.util.ArrayList;

import voyatrip.command.exceptions.InvalidArgumentValue;
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
            throw new InvalidArgumentValue();
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
        throw new InvalidArgumentValue();
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
