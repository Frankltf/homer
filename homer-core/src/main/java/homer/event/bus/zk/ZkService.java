package homer.event.bus.zk;


import homer.event.bus.annotation.PublicListenner;
import homer.event.bus.core.RegistyKeyFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

/**
 * @Intro
 * @Author liutengfei
 */
public class ZkService {
    private static final Logger LOGGER = LoggerFactory.getLogger(ZkService.class);
    @Autowired
    private ZkClient zkClient;
    @Autowired
    private RegistyKeyFactory registyKeyFactory;

    public void register(Class<? extends ApplicationListener> listener, String url){
        PublicListenner annotation = listener.getAnnotation(PublicListenner.class);
        String registerAddress = registyKeyFactory.getRegisterListenerAddress(annotation.scope(), listener);
        if(!zkClient.isExisted(registerAddress)){
            LOGGER.debug("register address start,key=" + registerAddress);
            zkClient.registerEphemeral(registerAddress, url,true);
        }
    }



    public void updateRegister(Class<? extends ApplicationListener> listener, String url){
        String registerAddress = registyKeyFactory.getRegisterListenerAddress("", listener);
        if(zkClient.isExisted(registerAddress)){
            LOGGER.debug("register update ,address = "+ registerAddress);
            zkClient.update(registerAddress, url);
        }else {
            LOGGER.error("register update fail,please check your zk");
            register(listener, url);
        }
    }


}
