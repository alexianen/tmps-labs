package model;

public class GameShop extends Business{
    public GameShop() {
        setType("Game Shop");
        addWorker(new Cashier());
    }
}
