package voyatrip;

import org.json.JSONObject;

import java.util.logging.Logger;
import java.util.logging.Level;

public class Transportation {
    private String name;
    private String mode;
    private Integer budget;
    private Integer day;


    private Logger logger = Logger.getLogger(Transportation.class.getName());

    public Transportation(String name, String mode, Integer budget, Integer day) {
        assert budget > 0;
        logger.log(Level.INFO, "Creating Transportation");
        this.name = name;
        this.mode = mode;
        this.budget = budget;
        this.day = day;

        logger.log(Level.INFO, "Transportation created");
    }

    public Integer getBudget() {
        return budget;
    }

    public void setBudget(Integer budget) {
        this.budget = budget;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public void setDay(Integer day) {
        this.day = day;
    }

    @Override
    public String toString() {
        return "Transportation by " + mode + " " + name + " on day " + day + " with budget $" + budget;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Transportation)) {
            return false;
        }

        boolean nameEquals = name.equals(((Transportation) obj).name);
        boolean budgetEquals = budget.equals(((Transportation) obj).budget);
        boolean modeEquals = mode.equals(((Transportation) obj).mode);
        boolean dayEquals = day.equals(((Transportation) obj).day);

        return nameEquals && budgetEquals && modeEquals && dayEquals;
    }

    // the following methods are for loading and saving the transportation data

    /**
     * The method is used to convert the transportation object to a JSON object.
     * @return JSON object that represents the transportation object.
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("mode", mode);
        json.put("budget", budget);
        json.put("day", day);

        return json;
    }

    /**
     * The method is used to convert the JSON object to a transportation object.
     * @param json JSON object that represents the transportation object.
     * @return Transportation object that represents the JSON object.
     */
    public static Transportation fromJson(JSONObject json) {
        String name = json.getString("name");
        String mode = json.getString("mode");
        Integer budget = json.getInt("budget");
        Integer day = json.getInt("day");

        return new Transportation(name, mode, budget, day);
    }
}
