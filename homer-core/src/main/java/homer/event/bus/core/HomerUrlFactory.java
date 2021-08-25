package homer.event.bus.core;

import org.springframework.context.ApplicationListener;

/**
 * @Intro
 * @Author liutengfei
 */
public interface HomerUrlFactory {
    String getUrl(ApplicationListener applicationListener, String key);
}
