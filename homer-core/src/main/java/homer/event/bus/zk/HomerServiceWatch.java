package homer.event.bus.zk;

import homer.event.bus.config.UrlModel;
import homer.event.bus.core.GenericHomerContext;
import homer.event.bus.utils.UrlUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Intro
 * @Author liutengfei
 */
public class HomerServiceWatch implements ServiceNodeWatch {
    private Logger logger = LoggerFactory.getLogger(HomerServiceWatch.class);

    @Autowired
    private GenericHomerContext genericHomerContext;

    public HomerServiceWatch( ) {
    }

    @Override
    public void onServiceAdded(String path, String dataStr) {
        UrlUtil.UrlEntity urlEntity = UrlUtil.parse(dataStr);
        UrlModel urlModel = new UrlModel(urlEntity);
        genericHomerContext.add(urlModel);
        logger.debug("onServiceAdded");
    }

    @Override
    public void onServiceUpdated(String path, String dataStr) {
        UrlUtil.UrlEntity urlEntity = UrlUtil.parse(dataStr);
        UrlModel urlModel = new UrlModel(urlEntity);
        genericHomerContext.del(urlModel);
        genericHomerContext.add(urlModel);
        logger.debug("onServiceUpdated");
    }


    @Override
    public void onServiceRemoved(String path, String dataStr) {
        UrlUtil.UrlEntity urlEntity = UrlUtil.parse(dataStr);
        UrlModel urlModel = new UrlModel(urlEntity);
        genericHomerContext.del(urlModel);
        logger.debug("onServiceRemoved");
    }
}
