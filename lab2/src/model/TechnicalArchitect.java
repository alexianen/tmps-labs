package model;

import output.Output;

public class TechnicalArchitect implements Worker {
    @Override
    public void doWork(Output output) {
        System.out.println("I'm the lead architect");
    }
}
