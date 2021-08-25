package homer.event.bus.demo;

import homer.event.bus.config.HomerConfig;
import homer.event.bus.config.ZookeeperConfig;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Intro
 * @Author liutengfei
 */
@Configuration
public class Config {
    @Bean
    public ZookeeperConfig zkConnectModel(){
        ZookeeperConfig zkConnectModel = new ZookeeperConfig();
        zkConnectModel.setConnectionTimeoutMs(1000);
        zkConnectModel.setConnectString("127.0.0.1:2183");
        zkConnectModel.setNamespace("homer");
        ExponentialBackoffRetry exponentialBackoffRetry = new ExponentialBackoffRetry(5000,5);
        zkConnectModel.setRetryPolicy(exponentialBackoffRetry);
        zkConnectModel.setSessionTimeoutMs(1000);
        return zkConnectModel;
    }

    @Bean
    public HomerConfig homerConfig(ZookeeperConfig zookeeperConfig){
        HomerConfig homerConfig = new HomerConfig();
        homerConfig.setZookeeperConfig(zookeeperConfig);
        return homerConfig;
    }
}
