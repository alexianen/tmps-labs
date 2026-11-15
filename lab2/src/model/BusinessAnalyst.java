package model;

import output.Output;

public class BusinessAnalyst implements Worker{
    @Override
    public void doWork(Output output) {
        System.out.println("I'm the business analyst");
    }
}
