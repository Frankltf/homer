package homer.event.bus.core;

import homer.event.bus.config.UrlModel;
import homer.event.bus.exception.ListenerFactoryException;
import homer.event.bus.model.AppHomer;
import homer.event.bus.model.IpHomer;
import homer.event.bus.model.ScopeHomer;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Intro
 * @Author liutengfei
 */
public class ListenerFactory implements ListenerRegistry {
    private Map<String, ScopeHomer> scopeHomerMap = new ConcurrentHashMap<>();

    public Map<String, ScopeHomer> getScopeHomerMap() {
        return scopeHomerMap;
    }

    private EventPushFactory eventPushFactory;

    public ListenerFactory(EventPushFactory eventPushFactory) {
        this.eventPushFactory = eventPushFactory;
    }

    @Override
    public void add(UrlModel urlModel){
        try {
            String address = urlModel.getIp()+ ":" + urlModel.getPort();
            if(!scopeHomerMap.containsKey(urlModel.getScope())){
                ScopeHomer scopeHomer = new ScopeHomer();
                AppHomer appHomer = new AppHomer(urlModel.getApp());
                IpHomer ipHomer = new IpHomer(address, eventPushFactory);
                ipHomer.addListener(urlModel.getEvent(), urlModel.getListener());
                appHomer.addIp(address, ipHomer);
                scopeHomer.addApp(urlModel.getEvent(), appHomer);
                scopeHomerMap.put(urlModel.getScope(), scopeHomer);
            }else {
                ScopeHomer scopeHomer = scopeHomerMap.get(urlModel.getScope());
                AppHomer appHomer = null;
                if(scopeHomer.contain(urlModel.getEvent())){
                    appHomer = scopeHomer.getAppHomer(urlModel.getEvent(),urlModel.getApp());
                    if(null == appHomer){
                        appHomer = new AppHomer(urlModel.getApp());
                    }
                }else{
                    appHomer = new AppHomer(urlModel.getApp());
                }
                if(appHomer.contain(address)){
                    IpHomer ipHomer = appHomer.getIp(address);
                    if(!ipHomer.containListener(urlModel.getEvent(), urlModel.getListener())){
                        ipHomer.addListener(urlModel.getEvent(), urlModel.getListener());
                    }
                }else{
                    IpHomer ipHomer = new IpHomer(address, eventPushFactory);
                    ipHomer.setAddress(address);
                    Map<String, Set<String>> listenerMap = new ConcurrentHashMap<>();
                    Set<String> listeners = new HashSet<>();
                    listeners.add(urlModel.getListener());
                    listenerMap.put(urlModel.getEvent(), listeners);
                    ipHomer.setListenerMap(listenerMap);
                    appHomer.addIp(address, ipHomer);
                }
                scopeHomer.addApp(urlModel.getEvent(), appHomer);
            }
        }catch (Exception e){
            throw new ListenerFactoryException("ListenerFactory add fail");
        }
    }

    @Override
    public void del(UrlModel urlModel) {
        try {
            String address = urlModel.getIp()+ ":" + urlModel.getPort();
            if(!scopeHomerMap.containsKey(urlModel.getScope())){
                return;
            }
            if(!scopeHomerMap.get(urlModel.getScope()).contain(urlModel.getEvent())){
                return;
            }
            if(!scopeHomerMap.get(urlModel.getScope()).getAppHomer(urlModel.getEvent(), urlModel.getApp())
                    .getIp(address).containListener(urlModel.getEvent(), urlModel.getListener())){
                return;
            }
            scopeHomerMap.get(urlModel.getScope()).getAppHomer(urlModel.getEvent(), urlModel.getApp()).getIp(address).
                    removeListener(urlModel.getEvent(), urlModel.getListener());
            if(scopeHomerMap.get(urlModel.getScope()).isEmpty()){
                scopeHomerMap.remove(urlModel.getScope());
            }else{
                if(scopeHomerMap.get(urlModel.getScope()).getAppHomer(urlModel.getEvent(), urlModel.getApp()).isEmpty()){
                    scopeHomerMap.get(urlModel.getScope()).removeAppHomer(urlModel.getEvent(), urlModel.getApp());
                }
            }


        }catch (Exception e){
            throw new ListenerFactoryException("ListenerFactory del fail");
        }
    }

}
