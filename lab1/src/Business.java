import java.util.ArrayList;
import java.util.List;

public class Business {
    private String name;
    private String location;
    private int numWorkers;
    private float revenue;
    private final List<Worker> workers = new ArrayList<>();
    private final List<Department> departments = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public int getNumWorkers() {
        return numWorkers;
    }

    public void setNumWorkers(int numWorkers) {
        this.numWorkers = numWorkers;
    }

    public float getRevenue() {
        return revenue;
    }

    public void setRevenue(float revenue) {
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

    public void open() {
        System.out.println(name + " is now open.");
    }

    public void performWork() {
        for (Worker worker : workers) {
            worker.doWork();
        }
    }

    public void printSummary(Output output) {
        output.println("Business: " + name);
        output.println("Location: " + location);
        output.println("Workers: " + numWorkers);
        output.println("Revenue: " + revenue);
        output.println("Departments: " + departments.size());

        for (Department department : departments) {
            output.println("- " + department.getName() + " (" + department.getNumWorkers() + " workers)");
        }
    }
}
