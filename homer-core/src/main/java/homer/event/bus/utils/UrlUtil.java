package homer.event.bus.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Intro
 * @Author liutengfei
 */
public class UrlUtil {
    public static class UrlEntity {

        public String baseUrl;
        public String protocol;
        public String ip;
        public String port;

        public Map<String, String> params;
    }

    public static UrlEntity parse(String url) {
        UrlEntity entity = new UrlEntity();
        if (url == null) {
            return entity;
        }
        url = url.trim();
        if (url.equals("")) {
            return entity;
        }
        String[] urlParts = url.split("\\?");
        entity.baseUrl = urlParts[0];
        int start = url.indexOf("://");
        int end = url.indexOf("?");

        entity.protocol = url.substring(0,start);
        entity.ip = url.substring(start+3, end).split(":")[0];
        entity.port = url.substring(start+3, end).split(":")[1];

        //没有参数
        if (urlParts.length == 1) {
            return entity;
        }
        //有参数
        String[] params = urlParts[1].split("&");
        entity.params = new HashMap<>();
        for (String param : params) {
            String[] keyValue = param.split("=");
            entity.params.put(keyValue[0], keyValue[1]);
        }

        return entity;
    }

    public static String getEventName(String value){
        int start = value.indexOf("<");
        int end = value.indexOf(">");
        return value.substring(start + 1, end);
    }
}
