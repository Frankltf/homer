package homer.event.bus.config;

/**
 * @Intro
 * @Author liutengfei
 */
public class HomerConfig {
    private ZookeeperConfig zookeeperConfig;

    public ZookeeperConfig getZookeeperConfig() {
        return zookeeperConfig;
    }

    public void setZookeeperConfig(ZookeeperConfig zookeeperConfig) {
        this.zookeeperConfig = zookeeperConfig;
    }
}
