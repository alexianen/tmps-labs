package model;

import output.Output;

public class FullStackEngineer implements Worker{
    @Override
    public void doWork(Output output) {
        System.out.println("I'm the full stack engineer");
    }
}
