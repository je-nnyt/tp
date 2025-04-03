package voyatrip;

import java.util.logging.Logger;
import java.util.logging.Level;

import java.util.ArrayList;

public class Transportation {
    private String name;
    private String mode;
    private Integer budget;
    private Integer startDay;
    private Integer endDay;
    private ArrayList<Integer> days;


    private Logger logger = Logger.getLogger(Transportation.class.getName());

    public Transportation(String name, String mode, Integer budget, ArrayList<Integer> days) {
        assert budget > 0;
        logger.log(Level.INFO, "Creating Transportation");
        this.name = name;
        this.mode = mode;
        this.budget = budget;
        this.days = new ArrayList<>();

        //add days to list
        for (int i = startDay; i <= endDay; i++) {
            days.add(i);
        }

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

    @Override
    public String toString() {
        return "Transportation by " + mode + " " + name + " with budget $"
                + budget;
    }
}
