package homer.event.bus.demo;

import org.springframework.context.ApplicationEvent;

/**
 * @Intro
 * @Author liutengfei
 */
public class EventDemo extends ApplicationEvent {
    private String message;

    public EventDemo(Object source, String message) {
        super(source);
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}