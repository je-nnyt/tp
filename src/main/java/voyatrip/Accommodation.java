package voyatrip;

public class Accommodation {
    private String name;
    private Integer budget;

    public Accommodation(String name, Integer budget) {
        this.name = name;
        this.budget = budget;
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

    @Override
    public String toString() {
        return "Accommodation at " + name + " with budget $" + budget;
    }
}
