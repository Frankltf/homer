package homer.event.bus.core;

import homer.event.bus.config.UrlModel;

/**
 * @Intro
 * @Author liutengfei
 */
public interface ListenerRegistry {
    void add(UrlModel urlModel);
    void del(UrlModel urlModel);
}
