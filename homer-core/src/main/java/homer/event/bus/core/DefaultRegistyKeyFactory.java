package homer.event.bus.core;

import homer.event.bus.config.HomerConst;
import org.springframework.context.ApplicationListener;
import org.springframework.util.StringUtils;

/**
 * @Intro
 * @Author liutengfei
 */
public class DefaultRegistyKeyFactory implements RegistyKeyFactory {
    @Override
    public String getRegisterRootAddress(String scope){
        return HomerConst.HOMER_REGISTRY_ROOT + "/" + (StringUtils.isEmpty(scope)? "default" : scope);
    }

    @Override
    public String getRegisterListenerAddress(String scope, Class<? extends ApplicationListener> listener){
        return getRegisterRootAddress(scope) + "/" + listener.getName();
    }
}
