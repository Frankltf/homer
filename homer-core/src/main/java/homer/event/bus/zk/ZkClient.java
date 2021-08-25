package homer.event.bus.zk;

import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;

/**
 * @Intro
 * @Author liutengfei
 * @Date 2021-04-28 11:25
 */
public interface ZkClient {
    void update(final String key, final String value);
    void remove(final String key);
    boolean isExisted(final String key);
    CuratorFramework getClient();
    void registerEphemeralSequential(final String key, final String value, boolean cacheNode);
    void registerEphemeral(final String key, final String value, boolean cacheNode);
    void registerListener(PathChildrenCacheListener listener);
    void registerEphemeralTransation(final String key, final String value, boolean cacheNode);
}
