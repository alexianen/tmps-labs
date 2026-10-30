import java.util.ArrayList;
import java.util.List;

public class Business extends BusinessFactory {
    private String name;
    private String location;
    private int numWorkers;
    private float revenue;
    private final List<Worker> workers = new ArrayList<>();

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
        workers.add(worker);
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
    }

    @Override
    protected Business createBusiness() {
        return new Business();
    }
}