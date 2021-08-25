package homer.event.bus.spring;

import homer.event.bus.core.HomerBootstrap;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.event.ContextRefreshedEvent;

/**
 * @Intro
 * @Author liutengfei
 */
public class HomerBootstrapApplicationListener implements ApplicationListener {

    private ConfigurableApplicationContext context;

    public HomerBootstrapApplicationListener(ConfigurableApplicationContext context) {
        this.context = context;
    }

    @Override
    public void onApplicationEvent(ApplicationEvent applicationEvent) {
        if(applicationEvent instanceof ContextRefreshedEvent){
            HomerBootstrap homerBootstrap = new HomerBootstrap(context);
            homerBootstrap.start();
        }
    }
}
