package homer.event.bus.exception;

/**
 * @Intro
 * @Author liutengfei
 * @Date 2021-04-28 11:09
 */
public class ZKException extends RuntimeException {
    public ZKException(String message) {
        super(message);
    }

    public ZKException(Throwable cause) {
        super(cause);
    }
}
