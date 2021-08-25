package homer.event.bus.model;


import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Intro
 * @Author liutengfei
 */
public class ScopeHomer {
    private Map<String, Set<AppHomer>> homerMap = new ConcurrentHashMap<>();

    public void addApp(String event,AppHomer appHomer){
        if(homerMap.containsKey(event)){
            Set<AppHomer> homers = homerMap.get(event);
            homers.add(appHomer);
        }
        Set<AppHomer> homerSet =new HashSet<>();
        homerSet.add(appHomer);
        homerMap.put(event,homerSet);
    }

    public Set<AppHomer> getHomers(String event){
        if(homerMap.containsKey(event)){
            return homerMap.get(event);
        }
        Set<AppHomer> homerSet =new HashSet<>();
        return homerSet;
    }

    public AppHomer getAppHomer(String event, String appName){
        if(homerMap.containsKey(event)){
            for (AppHomer appHomer: homerMap.get(event)){
                if(appHomer.getAppName().equals(appName)){
                    return appHomer;
                }
            }
            return null;
        }
        return null;
    }

    public Boolean removeAppHomer(String event, String appName){
        if(homerMap.containsKey(event)){
            for (AppHomer appHomer: homerMap.get(event)){
                if(appHomer.getAppName().equals(appName)){
                    homerMap.get(event).remove(appHomer);
                    return true;
                }
            }
            return false;
        }
        return false;
    }

    public Boolean contain(String event){
        return homerMap.containsKey(event);
    }

    public Boolean isEmpty(){
        return homerMap.values().isEmpty();
    }




}
