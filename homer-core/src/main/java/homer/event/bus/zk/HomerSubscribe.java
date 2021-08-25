package homer.event.bus.zk;

import homer.event.bus.config.HomerConst;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.annotation.AnnotationAttributes;

import java.util.Arrays;

/**
 * @Intro
 * @Author liutengfei
 */
public class HomerSubscribe implements InitializingBean {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomerSubscribe.class);
    private AnnotationAttributes enableHomer;

    private CuratorFramework client;

    @Autowired
    private PathChildrenCacheListener listener;

    public HomerSubscribe(AnnotationAttributes enableHomer) {
        this.enableHomer = enableHomer;
    }

    public void setClient(CuratorFramework client) {
        this.client = client;
    }

    private void doSubscribe(String scope){
        try {
            PathChildrenCache cache = new PathChildrenCache(client, HomerConst.HOMER_REGISTRY_ROOT + "/" + scope,true);
            cache.start();
            cache.getListenable().addListener(listener);
        }catch (Exception e){
            LOGGER.error("doSubscribe fail");
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        String[] scopes = enableHomer.getStringArray("scopes");
        if(null != scopes){
            Arrays.asList(scopes).forEach((value) -> {
                doSubscribe(value);
            });
        }
    }
}
