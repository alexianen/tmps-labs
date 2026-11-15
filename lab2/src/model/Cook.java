package model;

import output.Output;

public class Cook implements Worker {

    public void doWork(Output output) {
        output.println("I cook!");
    }
}
