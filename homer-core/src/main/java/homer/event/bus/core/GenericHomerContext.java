package homer.event.bus.core;

import homer.event.bus.config.UrlModel;
import homer.event.bus.model.AppHomer;
import homer.event.bus.model.IpHomer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;

import java.util.Set;

import static java.net.InetAddress.getLocalHost;

/**
 * @Intro
 * @Author liutengfei
 */
public class GenericHomerContext implements HomerContext, InitializingBean, EnvironmentAware, HomerEventPublisher {
    private ListenerFactory listenerFactory;

    @Autowired
    private EventPushFactory eventPushFactory;

    public GenericHomerContext() {

    }
    private Environment environment;
    private String address;
    @Autowired
    private ApplicationContext applicationContext;

    @Autowired
    private HomerRoute homerRoute;

    @Override
    public void add(UrlModel urlModel) {
        listenerFactory.add(urlModel);
    }

    @Override
    public void del(UrlModel urlModel) {
        listenerFactory.del(urlModel);
    }

    @Override
    public void publishEvent(Object event) {
        if(event instanceof ApplicationEvent){
            listenerFactory.getScopeHomerMap().forEach((key,value) -> {
                Set<AppHomer> appHomerSet = value.getHomers(event.getClass().getName());
                doAppHomer(appHomerSet, (ApplicationEvent) event);
            });
        }

    }

    private void doAppHomer(Set<AppHomer> appHomerSet, ApplicationEvent event){
        appHomerSet.forEach((value) -> {
            IpHomer ipHomer = value.getIpByBalance(homerRoute);
            Set<String> listeners = ipHomer.getListeners(event.getClass().getName());
            doListeners(listeners,ipHomer,event );
        });
    }

    private void doListeners(Set<String> listeners, IpHomer ipHomer, ApplicationEvent event){
        listeners.forEach((value) -> {
            EventPush eventPush = ipHomer.getEventPush(value);
            if(this.address.equals(ipHomer.getAddress())){
                applicationContext.publishEvent(event);
            }else{
                Object o= eventPush.push(event);
            }
        });
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String port = environment.getProperty("server.port","false");
        String hostAddress = getLocalHost().getHostAddress();
        this.address = hostAddress + ":" + port;
        this.listenerFactory = new ListenerFactory(eventPushFactory);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;

    }
}
