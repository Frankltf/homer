package homer.event.bus.zk;


public interface ServiceNodeWatch {

    void onServiceAdded(String path, String dataStr);

    void onServiceUpdated(String path, String dataStr);

    void onServiceRemoved(String path, String dataStr);

}
