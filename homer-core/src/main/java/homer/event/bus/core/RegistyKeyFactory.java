package homer.event.bus.core;

import org.springframework.context.ApplicationListener;

/**
 * @Intro
 * @Author liutengfei
 */
public interface RegistyKeyFactory {
    String getRegisterRootAddress(String scope);
    String getRegisterListenerAddress(String scope, Class<? extends ApplicationListener> listener);
}
