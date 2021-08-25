package homer.event.bus.demo;

import homer.event.bus.annotation.PublicListenner;
import org.springframework.context.ApplicationListener;

/**
 * @Intro
 * @Author liutengfei
 */
//@PublicListenner(scope = "scope2")
public class EventDemoListener2 implements ApplicationListener<EventDemo> {
    @Override
    public void onApplicationEvent(EventDemo event) {
        System.out.println("receiver " + event.getMessage());
    }
}
