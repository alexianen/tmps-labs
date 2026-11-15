package model;

import output.Output;

public class Cashier implements Worker {

    @Override
    public void doWork(Output output) {
        System.out.println("I sell!");
    }
}
