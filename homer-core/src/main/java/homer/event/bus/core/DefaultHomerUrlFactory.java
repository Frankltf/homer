package homer.event.bus.core;

import homer.event.bus.annotation.PublicListenner;
import homer.event.bus.utils.UrlUtil;
import org.springframework.context.ApplicationListener;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.net.UnknownHostException;

import static java.net.InetAddress.getLocalHost;

/**
 * @Intro
 * @Author liutengfei
 */
public class DefaultHomerUrlFactory implements HomerUrlFactory, EnvironmentAware {
    private Environment environment;
    @Override
    public String getUrl(ApplicationListener applicationListener, String key) {
        StringBuilder stringBuilder = new StringBuilder();
        PublicListenner publicListenner = applicationListener.getClass().getAnnotation(PublicListenner.class);
        Type[] interfaces = applicationListener.getClass().getGenericInterfaces();
        String event = UrlUtil.getEventName(interfaces[0].getTypeName());
        try {
            stringBuilder.append("homer://")
                    .append(getLocalHost().getHostAddress())
                    .append(":")
                    .append(StringUtils.isEmpty(environment.getProperty("server.port")) ? "8080" : environment.getProperty("local.server.port"))
                    .append("?listener=")
                    .append(applicationListener.getClass().getName())
                    .append("&beanName=")
                    .append(key)
                    .append("&event=")
                    .append(event)
                    .append("&app=")
                    .append(environment.getProperty("app.name","default"))
                    .append("&scope=")
                    .append(StringUtils.isEmpty(publicListenner.scope()) ? "default" : publicListenner.scope())
                    .append("&timestamp=")
                    .append(System.currentTimeMillis());
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
        return stringBuilder.toString();
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}
