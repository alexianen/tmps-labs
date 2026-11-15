package model;

public class Restaurant extends Business {
    public Restaurant() {
        setType("Restaurant");

        WorkerTeam kitchen = new WorkerTeam("Kitchen");
        kitchen.add(new Cook()).add(new Cook());

        addWorker(kitchen);
        addWorker(new Cashier());
    }
}
