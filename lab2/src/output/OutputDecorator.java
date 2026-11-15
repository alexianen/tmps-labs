package output;

/**
 * Decorator pattern: base class for every Output that wraps another Output.
 * A decorator IS an Output (so it can be used anywhere an Output is expected)
 * and HAS an Output (the one it forwards to after adding its own behaviour).
 */
public abstract class OutputDecorator implements Output {
    protected final Output wrapped;

    protected OutputDecorator(Output wrapped) {
        if (wrapped == null) {
            throw new IllegalArgumentException("Wrapped output cannot be null.");
        }
        this.wrapped = wrapped;
    }
}
