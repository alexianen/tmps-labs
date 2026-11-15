package output;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

/** Decorator that puts the current time in front of every non-empty line. */
public class TimestampOutput extends OutputDecorator {
    private static final DateTimeFormatter FORMAT = DateTimeFormatter.ofPattern("HH:mm:ss");

    public TimestampOutput(Output wrapped) {
        super(wrapped);
    }

    @Override
    public void println(String message) {
        if (message.isEmpty()) {
            wrapped.println(message);   // keep blank separator lines clean
            return;
        }
        wrapped.println("[" + LocalTime.now().format(FORMAT) + "] " + message);
    }
}