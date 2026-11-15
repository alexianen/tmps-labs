package model;

import output.Output;

public class ProductManager implements Worker{

    @Override
    public void doWork(Output output) {
        System.out.println("I'm the product manager");
    }
}
