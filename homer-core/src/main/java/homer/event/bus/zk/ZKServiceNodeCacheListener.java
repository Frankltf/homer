package homer.event.bus.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheEvent;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;

import java.nio.charset.Charset;


/**
 * @Intro
 * @Author liutengfei
 */
public class ZKServiceNodeCacheListener implements PathChildrenCacheListener {
    private Logger logger = LoggerFactory.getLogger(ZKServiceNodeCacheListener.class);
    private static final Charset DEFAULT_CHAR_SET = Charset.forName("UTF-8");

    @Autowired
    private ServiceNodeWatch listener;

    @Override
    public void childEvent(CuratorFramework curatorFramework, PathChildrenCacheEvent pathChildrenCacheEvent) throws Exception {
        PathChildrenCacheEvent.Type type = pathChildrenCacheEvent.getType();

        String dataStr = new String(pathChildrenCacheEvent.getData().getData(),DEFAULT_CHAR_SET);
        logger.debug("ZK node data change="+ type+ ",data="+ dataStr);
        if(StringUtils.isEmpty(dataStr)){
            return;
        }
        switch (type) {
            case CHILD_ADDED:
                try {
                    listener.onServiceAdded(pathChildrenCacheEvent.getData().getPath(), dataStr);
                } catch (Exception e) {
                    logger.error("onServiceAdded fail" + dataStr,e );
                }
                break;
            case CHILD_REMOVED:
                try {
                    listener.onServiceRemoved(pathChildrenCacheEvent.getData().getPath(), dataStr);
                } catch (Exception e) {
                    logger.error("onServiceRemoved fail" + dataStr,e );
                }
                break;
            case CHILD_UPDATED:
                try {
                    listener.onServiceUpdated(pathChildrenCacheEvent.getData().getPath(), dataStr);
                } catch (Exception e) {
                    logger.error("onServiceUpdated fail" + dataStr ,e);
                }
                break;


        }


    }
}
