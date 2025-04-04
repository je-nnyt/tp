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
    private Integer numDays;
    private ArrayList<Integer> days;


    private Logger logger = Logger.getLogger(Transportation.class.getName());

    public Transportation(String name, String mode, Integer budget, Integer startDay, Integer endDay) {
        assert budget > 0;
        logger.log(Level.INFO, "Creating Transportation");
        this.name = name;
        this.mode = mode;
        this.budget = budget;
        this.days = new ArrayList<>();
        this.numDays = 0;
        this.startDay = startDay;
        this.endDay = endDay;

        //add days to list
        for (int i = startDay; i <= endDay; i++) {
            days.add(i);
        }

        //calculate numDays
        calculateNumDay();

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

    public Integer getStartDay() {
        return startDay;
    }

    public void setStartDay(Integer startDay) {
        this.startDay = startDay;
    }

    public Integer getEndDay() {
        return endDay;
    }

    public void setEndDay(Integer endDay) {
        this.endDay = endDay;
    }

    public Integer getNumDays() {
        return numDays;
    }
    public void setNumDays(Integer numDays) {
        this.numDays = numDays;
    }

    @Override
    public String toString() {
        return "Transportation by " + mode + " " + name + " from day " + startDay
                + " to day " + endDay + " with budget $" + budget;
    }

    private void calculateNumDay() {
        if (startDay != null && endDay != null) {
             numDays = endDay - startDay;
        }
    }
}
