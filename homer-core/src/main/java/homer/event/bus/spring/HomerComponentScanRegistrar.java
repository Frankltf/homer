package homer.event.bus.spring;

import homer.event.bus.annotation.EnableHomer;
import homer.event.bus.core.ListenerConfig;
import homer.event.bus.core.ListenerExport;
import homer.event.bus.zk.HomerSubscribe;
import org.apache.curator.framework.CuratorFramework;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.RuntimeBeanReference;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.type.AnnotationMetadata;

import static com.alibaba.spring.util.BeanRegistrar.registerInfrastructureBean;
import static homer.event.bus.utils.SpiUtil.spiRegisty;
import static org.springframework.beans.factory.support.BeanDefinitionBuilder.rootBeanDefinition;

/**
 * @Intro
 * @Author liutengfei
 */
public class HomerComponentScanRegistrar implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        registerServiceBeanPostProcessor(registry);
        registerCommonBeans(registry);
        registrySpi(registry);
        BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition();
        AnnotationAttributes attributes = AnnotationAttributes.fromMap(
                importingClassMetadata.getAnnotationAttributes(EnableHomer.class.getName()));
        beanDefinitionBuilder.getBeanDefinition().setBeanClass(HomerSubscribe.class);
        beanDefinitionBuilder.getBeanDefinition().getConstructorArgumentValues().addGenericArgumentValue(attributes);
        beanDefinitionBuilder.getBeanDefinition().getPropertyValues().addPropertyValue("client",new RuntimeBeanReference(CuratorFramework.class.getName()));
        registry.registerBeanDefinition(HomerSubscribe.class.getName(),beanDefinitionBuilder.getBeanDefinition());

    }

    private void registrySpi(BeanDefinitionRegistry registry){
        spiRegisty(ListenerExport.class,registry);
    }

    private void registerServiceBeanPostProcessor(BeanDefinitionRegistry registry){
        BeanDefinitionBuilder builder = rootBeanDefinition(HomerServiceClassPostProcessor.class);
        builder.setRole(BeanDefinition.ROLE_INFRASTRUCTURE);
        AbstractBeanDefinition beanDefinition = builder.getBeanDefinition();
        BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinition, registry);
    }

    private void registerCommonBeans(BeanDefinitionRegistry registry) {
        registerInfrastructureBean(registry, HomerApplicationListenerRegistrar.BEAN_NAME, HomerApplicationListenerRegistrar.class);
        registerInfrastructureBean(registry, ListenerConfig.class.getName(), ListenerConfig.class);
    }

}
