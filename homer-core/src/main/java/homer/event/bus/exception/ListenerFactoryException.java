package homer.event.bus.exception;

/**
 * @Intro
 * @Author liutengfei
 */
public class ListenerFactoryException extends RuntimeException {
    public ListenerFactoryException(String message) {
        super(message);
    }

    public ListenerFactoryException(Throwable cause) {
        super(cause);
    }
}
