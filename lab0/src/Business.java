// separate bussinesses locations

public class Business implements Reportable, Employable {

    public String name;
    public String location;
    public int numWorkers;
    public float revenue;
    public boolean isOpen;

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
    public boolean isOpen() {
        return isOpen;
    }
    public void setOpen(boolean open) {
        isOpen = open;
    }


    @Override
    public void hireWorkers() {

    }

    @Override
    public void fireWorkers() {

    }
}
