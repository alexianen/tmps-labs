package model;

public class Restaurant extends Business {
    public Restaurant() {
        setType("Restaurant");
        addWorker(new Cook());
        addWorker(new Cashier());
    }
}
