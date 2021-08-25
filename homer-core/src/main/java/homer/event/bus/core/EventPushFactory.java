package homer.event.bus.core;

/**
 * @Intro
 * @Author liutengfei
 */
public interface EventPushFactory {
    EventPush addEventPush(String listener, String address);
}
