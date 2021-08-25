package homer.event.bus.model;

import homer.event.bus.core.HomerRoute;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * @Intro
 * @Author liutengfei
 */
public class AppHomer {
    private Map<String, IpHomer> ipHomers  = new ConcurrentHashMap<>();
    private String appName;

    public String getAppName() {
        return appName;
    }

    public AppHomer(String appName) {
        this.appName = appName;
    }

    public void addIp(String address, IpHomer ipHomer){
        if(ipHomers.containsKey(address)){
            ipHomers.remove(address);
        }
        ipHomers.put(address, ipHomer);
    }

    public void removeIp(String address){
        if(ipHomers.containsKey(address)){
            ipHomers.remove(address);
        }
    }

    public IpHomer getIp(String address){
        return ipHomers.get(address);
    }

    public Boolean contain(String address){
        return ipHomers.containsKey(address);
    }

    public Boolean isEmpty(){
        return ipHomers.values().isEmpty();
    }

    public IpHomer getIpByBalance(HomerRoute homerRoute){
        return homerRoute.getIpHomer(ipHomers.values().stream().collect(Collectors.toList()));
    }

}
