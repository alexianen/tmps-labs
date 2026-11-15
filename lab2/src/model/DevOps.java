package model;

import output.Output;

public class DevOps implements Worker{
    @Override
    public void doWork(Output output) {
        System.out.println("I'm the devops engineer");
    }
}
