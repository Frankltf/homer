package homer.event.bus.spring;

import feign.QueryMapEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import homer.event.bus.codec.DefaultDecoder;
import homer.event.bus.codec.DefaultEncoder;
import homer.event.bus.codec.ErrorDecoder;
import homer.event.bus.codec.FeignQueryMapEncoder;
import homer.event.bus.core.*;
import homer.event.bus.zk.*;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.recipes.cache.PathChildrenCacheListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;

import static com.alibaba.spring.util.BeanRegistrar.registerInfrastructureBean;

/**
 * @Intro
 * @Author liutengfei
 */
public class HomerServiceClassPostProcessor implements BeanDefinitionRegistryPostProcessor {
    private static final Logger LOGGER = LoggerFactory.getLogger(HomerServiceClassPostProcessor.class);

    public HomerServiceClassPostProcessor() {
    }

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        registerZk(beanDefinitionRegistry);

        registerInfrastructureBean(beanDefinitionRegistry, ZkService.class.getName(), ZkService.class);
        registerInfrastructureBean(beanDefinitionRegistry, RegistyKeyFactory.class.getName(), DefaultRegistyKeyFactory.class);
        registerInfrastructureBean(beanDefinitionRegistry, HomerUrlFactory.class.getName(), DefaultHomerUrlFactory.class);
        registerInfrastructureBean(beanDefinitionRegistry, ServiceNodeWatch.class.getName(), HomerServiceWatch.class);
        registerInfrastructureBean(beanDefinitionRegistry, PathChildrenCacheListener.class.getName(), ZKServiceNodeCacheListener.class);
        registerInfrastructureBean(beanDefinitionRegistry, HomerContext.class.getName(), GenericHomerContext.class);
        registerInfrastructureBean(beanDefinitionRegistry, HomerRoute.class.getName(), RandomHomerRoute.class);
        registerInfrastructureBean(beanDefinitionRegistry, EventPushFactory.class.getName(), DefaultEventPushFactory.class);
        registerInfrastructureBean(beanDefinitionRegistry, QueryMapEncoder.class.getName(), FeignQueryMapEncoder.class);
        registerInfrastructureBean(beanDefinitionRegistry, Encoder.class.getName(), DefaultEncoder.class);
        registerInfrastructureBean(beanDefinitionRegistry, Decoder.class.getName(), DefaultDecoder.class);
        registerInfrastructureBean(beanDefinitionRegistry, feign.codec.ErrorDecoder.class.getName(), ErrorDecoder.class);
    }

    private void registerZk(BeanDefinitionRegistry registry){
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
        beanDefinitionBuilder.getBeanDefinition().setBeanClass(CuratorFrameworkFactoryBean.class);
        beanDefinitionBuilder.getBeanDefinition().getConstructorArgumentValues().addGenericArgumentValue(new RuntimeBeanReference("homerConfig"));
        registry.registerBeanDefinition(CuratorFramework.class.getName(),beanDefinitionBuilder.getBeanDefinition());
        LOGGER.debug("[homer]zookeeper Curator =" + CuratorFramework.class.getName() + "has been registed");
        BeanDefinitionBuilder beanDefinitionBuilder1 = BeanDefinitionBuilder.genericBeanDefinition(HomerZKClient.class);
        beanDefinitionBuilder1.getBeanDefinition().getConstructorArgumentValues().
                addGenericArgumentValue(new RuntimeBeanReference(CuratorFramework.class.getName()));
        beanDefinitionBuilder1.getBeanDefinition().getPropertyValues().addPropertyValue("registyKeyFactory",new RuntimeBeanReference(RegistyKeyFactory.class.getName()));
        registry.registerBeanDefinition(ZkClient.class.getName(),beanDefinitionBuilder1.getBeanDefinition());
        LOGGER.debug("[homer]zookeeper client =" + HomerZKClient.class.getName() + "has been registed");
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {

    }
}
