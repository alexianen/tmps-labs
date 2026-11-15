package model;

import output.Output;

public class Itist implements Worker{
    @Override
    public void doWork(Output output) {
        System.out.println("I handle everything IT!");
    }
}
