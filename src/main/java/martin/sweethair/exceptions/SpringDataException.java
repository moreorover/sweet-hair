package martin.sweethair.exceptions;

public class SpringDataException  extends RuntimeException {

    public SpringDataException(String message) {
        super(message);
    }

    public SpringDataException(String message, Exception cause) {
        super(message, cause);
    }
}
