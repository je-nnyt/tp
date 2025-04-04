package voyatrip;

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
                + days.get(days.size() - 1) + " with budget $" + budget ;
    }
}
