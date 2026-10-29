public class Department {
    private final String name;
    private final int numWorkers;

    private Department(Builder builder) {
        this.name = builder.name;
        this.numWorkers = builder.numWorkers;
    }

    public String getName() {
        return name;
    }

    public int getNumWorkers() {
        return numWorkers;
    }

    public static class Builder {
        private String name;
        private int numWorkers;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder numWorkers(int numWorkers) {
            this.numWorkers = numWorkers;
            return this;
        }

        public Department build() {
            return new Department(this);
        }

    }
}
