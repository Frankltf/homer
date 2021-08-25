package homer.event.bus.core;

import homer.event.bus.annotation.PublicListenner;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ApplicationListener;
import org.springframework.core.annotation.AnnotationAwareOrderComparator;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @Intro
 * @Author liutengfei
 */
public class ListenerConfig implements ApplicationContextAware, InitializingBean {
    private Map<String, ApplicationListener> listenerMap;
    private ApplicationContext context;

    private void initHomerConfig(){
        Map<String, ApplicationListener> listeners = context.getBeansOfType(ApplicationListener.class);
        this.listenerMap = listeners
                .entrySet()
                .stream()
                .filter((value) -> {
                    return null != value.getValue().getClass().getAnnotation(PublicListenner.class);
                })
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }


    public void exportListeners(){
        listenerMap.forEach((key,value) -> {exportListener(value,key);});
    }

    private void exportListener(ApplicationListener applicationListener,String key){
        Map<String, ListenerExport> exportMap = context.getBeansOfType(ListenerExport.class);
        List<ListenerExport> exports = exportMap
                .values()
                .stream()
                .collect(Collectors.toList());
        AnnotationAwareOrderComparator.sort(exports);
        exports.forEach((value) -> {value.exportListener(applicationListener, key);});
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        initHomerConfig();
    }
}
