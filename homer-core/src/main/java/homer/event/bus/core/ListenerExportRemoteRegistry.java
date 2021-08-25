package homer.event.bus.core;

import homer.event.bus.zk.ZkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;

/**
 * @Intro
 * @Author liutengfei
 */
public class ListenerExportRemoteRegistry implements ListenerExport {
    @Autowired
    private ZkService zkService;

    @Autowired
    private HomerUrlFactory homerUrlFactory;



    @Override
    public void exportListener(ApplicationListener applicationListener,String key) {
        String url = homerUrlFactory.getUrl(applicationListener, key);
        zkService.register(applicationListener.getClass(), url);
    }

    @Override
    public int getOrder() {
        return 6;
    }
}
