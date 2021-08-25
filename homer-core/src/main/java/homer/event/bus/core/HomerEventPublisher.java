package homer.event.bus.core;

import org.springframework.context.ApplicationEvent;

/**
 * @Intro
 * @Author liutengfei
 */
public interface HomerEventPublisher {

    default void publishEvent(ApplicationEvent event) {
        publishEvent((Object) event);
    }

    void publishEvent(Object event);
}
