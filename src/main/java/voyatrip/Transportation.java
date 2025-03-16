package voyatrip;

public class Transportation {
    private String mode;
    private String name;
    private Integer budget;

    public Transportation(String mode) {
        this.mode = mode;
    }

    public Transportation(String mode, Integer budget) {
        this.mode = mode;
        this.budget = budget;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
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

}
