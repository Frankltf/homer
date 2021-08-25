package homer.event.bus.config;

import homer.event.bus.utils.UrlUtil;

/**
 * @Intro
 * @Author liutengfei
 */
public class UrlModel {
    private String scope;
    private String app;
    private String ip;
    private String port;
    private String listener;
    private String beanName;
    private String timestamp;
    private String protocol;
    private String event;

    public UrlModel(UrlUtil.UrlEntity entity) {
        if(null != entity){
            this.scope = entity.params.get("scope");
            this.app = entity.params.get("app");
            this.ip = entity.ip;
            this.port = entity.port;
            this.listener = entity.params.get("listener");
            this.beanName = entity.params.get("beanName");
            this.timestamp = entity.params.get("timestamp");
            this.protocol = entity.protocol;
            this.event = entity.params.get("event");
        }
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getScope() {
        return scope;
    }

    public void setScope(String scope) {
        this.scope = scope;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }



    public String getListener() {
        return listener;
    }

    public void setListener(String listener) {
        this.listener = listener;
    }

    public String getBeanName() {
        return beanName;
    }

    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    @Override
    public String toString() {
        return "UrlModel{" +
                "scope='" + scope + '\'' +
                ", app='" + app + '\'' +
                ", ip='" + ip + '\'' +
                ", port='" + port + '\'' +
                ", listener='" + listener + '\'' +
                ", beanName='" + beanName + '\'' +
                ", timestamp='" + timestamp + '\'' +
                ", protocol='" + protocol + '\'' +
                ", event='" + event + '\'' +
                '}';
    }
}
