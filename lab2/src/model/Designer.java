package model;

import output.Output;

public class Designer implements Worker{
    @Override
    public void doWork(Output output) {
        System.out.println("I design the site!");
    }
}
