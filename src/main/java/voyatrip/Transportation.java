package voyatrip;

public class Transportation {
    private String name;
    private String mode;
    private Integer budget;

    public Transportation(String name, String mode, Integer budget) {
        this.name = name;
        this.mode = mode;
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

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Override
    public String toString() {
        return "Transportation" + "by " + mode + " " + name + " with budget " + budget;
    }
}
