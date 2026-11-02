package model;

public class ITCompany extends Business{
    public ITCompany() {
        setType("IT Company");
        addWorker(new Itist());
        addWorker(new Designer());
    }
}
