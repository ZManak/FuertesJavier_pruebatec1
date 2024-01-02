package controlsys.exceptions;

import java.util.ArrayList;
import java.util.List;

public class InvalidDataException extends Exception {
    List<String> messages;

    public InvalidDataException() throws InvalidDataException {
        super("Invalid data");
        messages = new ArrayList<>();
        if (!messages.isEmpty()) {
            throw new InvalidDataException(messages);
        }
    }

    public InvalidDataException(List<String> messages) {
        this.messages = messages;
    }

    public List<String> getMessages() {
        return messages;
    }
}
