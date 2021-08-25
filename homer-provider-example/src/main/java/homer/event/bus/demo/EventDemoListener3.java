package homer.event.bus.demo;

import homer.event.bus.annotation.PublicListenner;
import org.springframework.context.ApplicationListener;

/**
 * @Intro
 * @Author liutengfei
 */
//@PublicListenner(scope = "scope1")
public class EventDemoListener3 implements ApplicationListener<EventDemo> {
    @Override
    public void onApplicationEvent(EventDemo event) {
        System.out.println("receiver " + event.getMessage());
    }

    public String requestMappingHandlerMapping(EventDemo event ){
        return "requestMappingHandlerMapping";
    }

}
