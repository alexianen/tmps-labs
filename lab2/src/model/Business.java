package model;

import output.Output;

import java.util.ArrayList;
import java.util.List;


public abstract class Business {
    private String name;
    private String location;
    private int numWorkers;
    private float revenue;
    private String type;
    private final List<Worker> workers = new ArrayList<>();
    private final List<Department> departments = new ArrayList<>();

    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Business name is required.");
        }
        this.name = name;
    }

    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        if (location == null || location.trim().isEmpty()) {
            throw new IllegalArgumentException("Business location is required.");
        }
        this.location = location;
    }

    public int getNumWorkers() {
        return numWorkers;
    }
    public void setNumWorkers(int numWorkers) {
        if (numWorkers < 0) {
            throw new IllegalArgumentException("Number of workers cannot be negative.");
        }
        if (numWorkers < getDepartmentWorkersTotal()) {
            throw new IllegalArgumentException(
                    "Worker count cannot be lower than the workers already assigned to departments ("
                            + getDepartmentWorkersTotal() + ")."
            );
        }
        this.numWorkers = numWorkers;
    }

    public float getRevenue() {
        return revenue;
    }
    public void setRevenue(float revenue) {
        if (revenue < 0) {
            throw new IllegalArgumentException("Revenue cannot be negative.");
        }
        this.revenue = revenue;
    }

    public void addWorker(Worker worker) {
        if (worker == null) {
            throw new IllegalArgumentException("Worker cannot be null.");
        }
        workers.add(worker);
    }

    public int getDepartmentWorkersTotal() {
        int total = 0;
        for (Department department : departments) {
            total += department.getNumWorkers();
        }
        return total;
    }

    public void addDepartment(Department department) {
        if (department == null) {
            throw new IllegalArgumentException("Department cannot be null.");
        }

        int currentTotal = getDepartmentWorkersTotal();

        if (currentTotal + department.getNumWorkers() > numWorkers) {
            throw new IllegalArgumentException(
                    "The total workers in departments cannot exceed the business worker count (" + numWorkers + ")."
            );
        }

        departments.add(department);
    }

    public void open(Output output) {
        output.println(name + " is now open.");
    }

    public void performWork(Output output) {
        for (Worker worker : workers) {
            worker.doWork(output);
        }
    }

    public void printSummary(Output output) {
        output.println("Business type: " + type);
        output.println("Business: " + name);
        output.println("Location: " + location);
        output.println("Workers: " + numWorkers);
        output.println("Revenue: " + revenue);
        output.println("Departments: " + departments.size());

        for (Department d : departments) {
            output.println("- " + d.getName() + " (" + d.getNumWorkers() + " workers)");
            if (!d.getManager().isBlank()) output.println("    Manager: " + d.getManager());
            output.println("    Budget: " + d.getBudget());
            if (!d.getSocialMedia().isBlank()) output.println("    Social media: " + d.getSocialMedia());
        }
    }
}