package homer.event.bus.model;

import homer.event.bus.core.EventPush;
import homer.event.bus.core.EventPushFactory;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;


/**
 * @Intro
 * @Author liutengfei
 */
public class IpHomer {
    private Map<String, Set<String>> listenerMap = new ConcurrentHashMap<>();
    private Map<String, EventPush> eventPushMap = new ConcurrentHashMap<>();

    private String address;
    private EventPushFactory eventPushFactory;
    public IpHomer(String address, EventPushFactory eventPushFactory) {
        this.address = address;
        this.eventPushFactory = eventPushFactory;
    }

    public EventPush getEventPush(String listener){
        return eventPushMap.get(listener);
    }

    public boolean contain(String event){
        return listenerMap.containsKey(event);
    }

    public void addListener(String event, String listener){
        if(contain(event)){
            listenerMap.get(event).add(listener);
        }else{
            Set<String> listeners = new HashSet<>();
            listeners.add(listener);
            listenerMap.put(event,listeners);
        }
        if(eventPushMap.containsKey(listener)){
            eventPushMap.remove(listener);
        }
        eventPushMap.put(listener, eventPushFactory.addEventPush(listener, address));
    }

    public void removeListener(String event, String listener){
        if(listenerMap.containsKey(event)){
            listenerMap.get(event).remove(listener);
        }
    }

    public Boolean containEvent(String event){
        return listenerMap.containsKey(event);
    }

    public Boolean containListener(String event, String listener){
        Set<String> listeners = listenerMap.get(event);
        if(null != listeners){
            for (String lis : listeners){
                if(lis.equals(listener)){
                    return true;
                }
            }
            return false;
        }else{
            return false;
        }
    }

    public Set<String> getListeners(String event){
        return listenerMap.get(event);
    }

    public Map<String, Set<String>> getListenerMap() {
        return listenerMap;
    }

    public void setListenerMap(Map<String, Set<String>> listenerMap) {
        this.listenerMap = listenerMap;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
