// separate bussinesses locations

public class Business implements Franchise {

    public String name;
    public String location;
    public int numWorkers;
    public float revenue;

    // getters and setters

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


    public final void createBusiness() {
        System.out.println("Congrats! We created " + getName());
        System.out.println("Located in:" + getLocation());
        System.out.println("Number of workers: " + getNumWorkers());
        System.out.println("Wow! We have a lot of revenue: " + getRevenue());
    }

    @Override
    public void manage() {

    }

    @Override
    public void hireWorkers() {

    }

    @Override
    public void fireWorkers() {

    }
}
