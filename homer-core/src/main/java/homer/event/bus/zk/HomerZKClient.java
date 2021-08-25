package homer.event.bus.zk;

import homer.event.bus.config.HomerConst;
import homer.event.bus.core.RegistyKeyFactory;
import homer.event.bus.exception.ZKException;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.api.transaction.CuratorTransaction;
import org.apache.curator.framework.api.transaction.CuratorTransactionResult;
import org.apache.curator.framework.recipes.cache.PathChildrenCache;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.apache.curator.framework.state.ConnectionState;
import org.apache.curator.framework.state.ConnectionStateListener;
import org.apache.zookeeper.CreateMode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import java.nio.charset.Charset;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @Intro
 * @Author liutengfei
 */
public class HomerZKClient implements ZkClient, InitializingBean {
    private CuratorFramework client;
    private static final Logger LOGGER = LoggerFactory.getLogger(HomerZKClient.class);
    private Map<String, String> ephemeralNodes = new LinkedHashMap<>(4);
    private Map<String, String> ephemeralSequentialNodes = new LinkedHashMap<>(1);
    private static final Charset DEFAULT_CHAR_SET = Charset.forName("UTF-8");
    private PathChildrenCache cache;
    private RegistyKeyFactory registyKeyFactory;
    public void setClient(CuratorFramework client) {
        this.client = client;
    }

    public void setRegistyKeyFactory(RegistyKeyFactory registyKeyFactory) {
        this.registyKeyFactory = registyKeyFactory;
    }

    public HomerZKClient(CuratorFramework client) {
        this.client = client;
    }

    @Override
    public void registerListener(PathChildrenCacheListener listener) {
        cache.getListenable().addListener(listener);
    }

    private void initLocalCache() throws Exception {
        cache.start();
    }

    private void addConnectionStateListener() {
        client.getConnectionStateListenable().addListener(new ConnectionStateListener() {
            @Override
            public void stateChanged(CuratorFramework client, ConnectionState newState) {

                if (newState == ConnectionState.RECONNECTED) {
                    for(String key : ephemeralNodes.keySet()){
                        LOGGER.info("zk连接发生重连，重新从cache中取临时节点数据重新注册,key：{}" + key);
                        reRegisterEphemeral(key,ephemeralNodes.get(key));
                    }

                    for(String key : ephemeralSequentialNodes.keySet()){
                        LOGGER.info("zk连接发生重连，重新从cache中取临时序列节点数据重新注册,key：{}" + key);
                        reRegisterEphemeralSequential(key,ephemeralSequentialNodes.get(key));
                    }
                }
                if(newState == ConnectionState.LOST){
                    LOGGER.error("zk丢失连接");
                }
            }
        });
    }

    public void reRegisterEphemeral(final String key, final String value) {
        registerEphemeral(key, value, false);
    }

    @Override
    public void registerEphemeralTransation(final String key, final String value, boolean cacheNode) {
        try {
            CuratorTransaction transaction = client.inTransaction();
            Collection<CuratorTransactionResult> results = transaction.check().forPath(key).and().create().
                    withMode(CreateMode.EPHEMERAL).forPath(key, value.getBytes(DEFAULT_CHAR_SET)).and().commit();
            if (cacheNode) {
                ephemeralNodes.put(key, value);
            }
        } catch (Exception ex) {
            LOGGER.error("registerEphemeralTransation:{},{}" + key + value, ex);
        }
    }

    @Override
    public void registerEphemeral(final String key, final String value, boolean cacheNode) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL).forPath(key, value.getBytes(DEFAULT_CHAR_SET));
            if (cacheNode) {
                ephemeralNodes.put(key, value);
            }
        } catch (Exception ex) {
            LOGGER.error("persistEphemeral:" + key + value, ex);
        }
    }

    @Override
    public void remove(final String key) {
        try {
            client.delete().deletingChildrenIfNeeded().forPath(key);
        } catch (Exception ex) {
            LOGGER.error("removeAndClose:{}" + key, ex);
            throw new ZKException(ex);
        }
    }

    @Override
    public void update(final String key, final String value) {
        try {
            client.setData().forPath(key,value.getBytes());
        } catch (Exception ex) {
            LOGGER.error("removeAndClose:{}" + key, ex);
            throw new ZKException(ex);
        }
    }

    @Override
    public boolean isExisted(final String key) {
        try {
            return null != client.checkExists().forPath(key);
        } catch (Exception ex) {
            LOGGER.error("isExisted:{}" + key, ex);
            return false;
        }
    }


    @Override
    public CuratorFramework getClient() {
        return client;
    }

    private void reRegisterEphemeralSequential(final String key, final String value) {
        registerEphemeralSequential(key, value, false);
    }

    @Override
    public void registerEphemeralSequential(final String key, final String value, boolean cacheNode) {
        try {
            client.create().creatingParentsIfNeeded().withMode(CreateMode.EPHEMERAL_SEQUENTIAL).forPath(key, value.getBytes());
            if (cacheNode){
                ephemeralSequentialNodes.put(key, value);
            };
        } catch (Exception ex) {
            LOGGER.error("persistEphemeralSequential:{},{}" + key + value, ex);
            throw new ZKException(ex);
        }
    }

    @Override
    public void afterPropertiesSet() throws Exception {
//        this.cache =  new PathChildrenCache(client, HomerConst.HOMER_REGISTRY_ROOT,true);
        addConnectionStateListener();
        try {
//            initLocalCache();
        } catch (Exception e) {
            throw new ZKException("zk connect fail");
        }
    }
}
