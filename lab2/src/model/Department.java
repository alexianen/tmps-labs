package model;

public class Department {
    // Builder means: instead of one huge constructor,
    // we fill in the details step by step on a helper object,
    // and it creates the finished, validated object when we say “build”.
    private final String depName;
    private final String manager;
    private final int numWorkers;
    private final double budget;
    private final String socialMedia;


    private Department(Builder builder) {
        // validation
        if (builder.depName == null || builder.depName.trim().isEmpty()) {
            throw new IllegalArgumentException("Department name is required.");
        }
        if (builder.numWorkers < 0) {
            throw new IllegalArgumentException("Number of workers cannot be negative.");
        }
        if (builder.budget < 0) {
            throw new IllegalArgumentException("Budget cannot be negative.");
        }
        this.depName = builder.depName;
        this.manager = builder.manager;
        this.numWorkers = builder.numWorkers;
        this.budget = builder.budget;
        this.socialMedia = builder.socialMedia;
    }

    public String getName() {
        return depName;
    }
    public String getManager() { return manager; }
    public int getNumWorkers() {
        return numWorkers;
    }
    public double getBudget() { return budget; }
    public String getSocialMedia() { return socialMedia; }

    public static class Builder {
        private String depName;
        private String manager = "";
        private int numWorkers;
        private double budget;
        private String socialMedia = "";

        public Builder depName(String depName) {
            this.depName = depName;
            return this;
        }
        public Builder manager(String manager) {
            this.manager = manager;
            return this;
        }
        public Builder numWorkers(int numWorkers) {
            this.numWorkers = numWorkers;
            return this;
        }
        public Builder budget(double budget) {

            this.budget = budget;
            return this;
        }
        public Builder socialMedia(String socialMedia) {
            this.socialMedia = socialMedia;
            return this;
        }

        public Department build() {
            return new Department(this);
        }

    }
}
