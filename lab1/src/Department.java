public class Department {
    private final String depName;
    private final String manager;
    private final int numWorkers;
    private final float budget;
    private final String socMed;



    Department(Builder builder) {
        this.depName = builder.depName;
        this.manager = builder.manager;
        this.numWorkers = builder.numWorkers;
        this.budget = builder.budget;
        this.socMed = builder.socMed;
    }

    public String getName() {
        return depName;
    }
    public String getManager() { return manager; }
    public int getNumWorkers() {
        return numWorkers;
    }
    public float getBudget() { return budget; }
    public String getSocMed() { return socMed; }

    public static class Builder {
        private String depName;
        private String manager;
        private int numWorkers;
        private float budget;
        private String socMed;

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
        public Builder budget(float budget) {
            this.budget = budget;
            return this;
        }
        public Builder socMed(String socMed) {
            this.socMed = socMed;
            return this;
        }

        public Department build() {
            return new Department(this);
        }

    }
}
