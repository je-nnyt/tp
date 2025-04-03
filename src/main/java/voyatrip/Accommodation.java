package voyatrip;

import java.util.ArrayList;

public class Accommodation {
    private String name;
    private Integer budget;
    private ArrayList<Integer> days;

    public Accommodation(String name, Integer budget, ArrayList<Integer> days) {
        this.name = name;
        this.budget = budget;
        this.days = days;
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
        this.days = days;
    }

    @Override
    public String toString() {
        return "Accommodation at " + name + " from day " + days.get(0) + " to day "
                + days.get(days.size() - 1) + " with budget $" + budget ;
    }
}
