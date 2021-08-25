package homer.event.bus.core;

import org.springframework.context.ApplicationListener;
import org.springframework.core.Ordered;

/**
 * @Intro
 * @Author liutengfei
 */
public interface ListenerExport extends Ordered {
    void exportListener(ApplicationListener applicationListener,String key);
}
