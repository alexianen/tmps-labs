package model;

import output.Output;
import output.PrefixOutput;

import java.util.ArrayList;
import java.util.List;

/**
 * Composite pattern: the "composite".
 * A team is a Worker that contains other Workers (single workers or other teams).
 * Asking the team to work asks every member to work.
 */
public class WorkerTeam implements Worker {
    private final String name;
    private final List<Worker> members = new ArrayList<>();

    public WorkerTeam(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Team name is required.");
        }
        this.name = name;
    }

    public WorkerTeam add(Worker member) {
        if (member == null) {
            throw new IllegalArgumentException("Worker cannot be null.");
        }
        if (member == this) {
            throw new IllegalArgumentException("A team cannot contain itself.");
        }
        members.add(member);
        return this;
    }

    public void remove(Worker member) {
        members.remove(member);
    }

    @Override
    public void doWork(Output output) {
        output.println("Team " + name + ":");
        // Decorator + Composite working together: members print one level deeper.
        Output indented = new PrefixOutput(output, "  ");
        for (Worker member : members) {
            member.doWork(indented);
        }
    }
}
