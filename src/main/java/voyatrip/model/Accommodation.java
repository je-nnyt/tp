package voyatrip.model;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Accommodation {
    private String name;
    private Integer budget;
    private ArrayList<Integer> days;
    private Logger logger = Logger.getLogger(Accommodation.class.getName());

    public Accommodation(String name, Integer budget, ArrayList<Integer> days) {
        assert budget > 0;
        assert !days.isEmpty();
        assert days.get(0) > 0;
        logger.log(Level.INFO, "Creating Accommodation");
        this.name = name;
        this.budget = budget;
        this.days = new ArrayList<>(days);
        logger.log(Level.INFO, "Accommodation created");
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

    public ArrayList<Integer> getDays() {
        return days;
    }

    public void setDays(ArrayList<Integer> days) {
        this.days = new ArrayList<>(days);
    }

    @Override
    public String toString() {
        return "Accommodation at " + name + " from day " + days.get(0) + " to day "
                + days.get(days.size() - 1) + " with budget $" + budget;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (!(obj instanceof Accommodation)) {
            return false;
        }

        boolean nameEquals = name.equals(((Accommodation) obj).name);
        boolean budgetEquals = budget.equals(((Accommodation) obj).budget);

        boolean daysEquals = true;
        for (int i = 0; i < days.size(); i++) {
            if (!days.get(i).equals(((Accommodation) obj).days.get(i))) {
                daysEquals = false;
                break;
            }
        }

        return nameEquals && budgetEquals && daysEquals;
    }

    // the following methods are for JSON serialization

    /**
     * Converts the Accommodation object to a JSON object.
     * @return JSON object representing the Accommodation.
     */
    public JSONObject toJson() {
        JSONObject json = new JSONObject();
        json.put("name", name);
        json.put("budget", budget);

        JSONArray daysArray = new JSONArray();
        for (Integer day : days) {
            daysArray.put(day);
        }
        json.put("days", daysArray);

        return json;
    }

    /**
     * Converts a JSON object to an Accommodation object.
     * @param json JSON object representing the Accommodation.
     * @return Accommodation object.
     */
    public static Accommodation fromJson(JSONObject json) {
        String name = json.getString("name");
        Integer budget = json.getInt("budget");
        ArrayList<Integer> days = new ArrayList<>();

        for (Object day : json.getJSONArray("days")) {
            days.add((Integer) day);
        }

        return new Accommodation(name, budget, days);
    }
}
