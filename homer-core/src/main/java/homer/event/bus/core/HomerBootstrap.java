package homer.event.bus.core;

import homer.event.bus.zk.ZkService;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.Lifecycle;

/**
 * @Intro
 * @Author liutengfei
 */
public class HomerBootstrap implements Lifecycle {
    private ConfigurableApplicationContext context;

    public HomerBootstrap(ConfigurableApplicationContext context) {
        this.context = context;
    }


    private void exportListeners(){
        ListenerConfig listenerConfig = context.getBean(ListenerConfig.class);
        listenerConfig.exportListeners();
    }

    private void subscribeListeners(){
//        ZkService zkService = context.getBean(ZkService.class);
//        zkService.watch();
    }

    @Override
    public void start() {
        exportListeners();
        subscribeListeners();
    }

    @Override
    public void stop() {

    }

    @Override
    public boolean isRunning() {
        return false;
    }
}
