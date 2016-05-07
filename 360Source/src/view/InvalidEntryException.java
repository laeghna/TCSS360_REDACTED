package view;

public class InvalidEntryException extends Exception {

    /**
     * 
     */
    private static final long serialVersionUID = 4830564698942912453L;

    /**
     * Initialize a new CircularDependencyException.
     */
    public InvalidEntryException() {
    }

    /**
     * Initializes a new CircularDependencyException.
     * 
     * @param message the message to be displayed when this exception occurs.
     */
    public InvalidEntryException(final String message) {
        super(message);
    }
}
