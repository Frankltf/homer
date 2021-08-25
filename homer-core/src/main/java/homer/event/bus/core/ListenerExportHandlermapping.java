package homer.event.bus.core;

import homer.event.bus.exception.ListenerExportHandlermappingException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @Intro
 * @Author liutengfei
 */
public class ListenerExportHandlermapping implements ListenerExport {
    public static final Logger LOGGER = LoggerFactory.getLogger(ListenerExportHandlermapping.class);
    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Override
    public void exportListener(ApplicationListener applicationListener,String key) {
        RequestMappingInfo mapping = createRequestMappingInfo(applicationListener);
        try {
            Class<?> cls = Class.forName(applicationListener.getClass().getName());
            Method[] methods = cls.getMethods();
            Arrays.asList(methods)
                    .stream()
                    .filter(value -> {return value.getName().equals("onApplicationEvent");})
                    .filter(value -> {
                        return value.getParameterTypes()[0] != ApplicationEvent.class;})
                    .forEach(value -> {
                        requestMappingHandlerMapping.registerMapping(mapping,key,value);
                        LOGGER.info("register success key:{},path:{}",key,applicationListener.getClass().getName());
                    });
        } catch (Exception e) {
            LOGGER.error("homer register success key:{},path:{}",key,applicationListener.getClass().getName());
            throw new ListenerExportHandlermappingException("homer register fail");
        }
    }




    protected RequestMappingInfo createRequestMappingInfo(ApplicationListener applicationListener) {
        RequestMappingInfo.Builder builder = RequestMappingInfo
                .paths(applicationListener.getClass().getName());
        return builder.build();
    }

    @Override
    public int getOrder() {
        return 4;
    }
}
