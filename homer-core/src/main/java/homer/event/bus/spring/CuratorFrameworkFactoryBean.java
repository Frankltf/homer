package homer.event.bus.spring;

import homer.event.bus.config.HomerConfig;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;

/**
 * @Intro
 * @Author liutengfei
 */
public class CuratorFrameworkFactoryBean implements FactoryBean<CuratorFramework>, DisposableBean {
    private HomerConfig homerConfig;
    private CuratorFramework client;
    public CuratorFrameworkFactoryBean(HomerConfig homerConfig) {
        this.homerConfig = homerConfig;
    }

    @Override
    public void destroy() throws Exception {
        if(null != this.client){
            this.client.close();
        }
    }

    @Override
    public CuratorFramework getObject() throws Exception {
        CuratorFramework client = CuratorFrameworkFactory.newClient(homerConfig.getZookeeperConfig().getConnectString(),
                homerConfig.getZookeeperConfig().getSessionTimeoutMs(),
                homerConfig.getZookeeperConfig().getConnectionTimeoutMs(),
                homerConfig.getZookeeperConfig().getRetryPolicy());
        client.start();
        this.client = client;
        return client;
    }

    @Override
    public Class<?> getObjectType() {
        return CuratorFramework.class;
    }
}
