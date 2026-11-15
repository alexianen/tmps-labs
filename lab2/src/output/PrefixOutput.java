package output;

/** Decorator that puts a fixed prefix (a tag, or indentation) in front of every non-empty line. */
public class PrefixOutput extends OutputDecorator {
    private final String prefix;

    public PrefixOutput(Output wrapped, String prefix) {
        super(wrapped);
        this.prefix = prefix == null ? "" : prefix;
    }

    @Override
    public void println(String message) {
        wrapped.println(message.isEmpty() ? message : prefix + message);
    }
}
