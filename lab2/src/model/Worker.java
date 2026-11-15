package model;

import output.Output;

/**
 * Composite pattern: the "component" type.
 * A single worker (leaf) and a whole team (composite) are both Workers,
 * so client code can treat them in exactly the same way.
 */
public interface Worker {
    void doWork(Output output);
}
