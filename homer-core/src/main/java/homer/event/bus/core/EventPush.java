package homer.event.bus.core;

import feign.QueryMap;
import feign.RequestLine;
import org.springframework.context.ApplicationEvent;

/**
 * @Intro
 * @Author liutengfei
 */
public interface EventPush {
    @RequestLine("POST ")
    Object push(@QueryMap ApplicationEvent event);
}
