package model;

import output.Output;

public class Cook implements Worker {

    @Override
    public void doWork(Output output) {
        output.println("I cook!");
    }
}
