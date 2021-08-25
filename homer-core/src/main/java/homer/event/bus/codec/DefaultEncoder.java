package homer.event.bus.codec;

import feign.RequestTemplate;
import feign.codec.EncodeException;
import feign.codec.Encoder;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Type;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import static homer.event.bus.codec.JsonEncodeProcessor.CONTENT_TYPE;

/**
 * @Intro
 * @Author liutengfei
 */
public class DefaultEncoder implements Encoder {
    private final Map<String, EncoderProcessor> processors = new HashMap<>(16);
    public DefaultEncoder() {
        JsonEncodeProcessor jsonEncodeProcessor = new JsonEncodeProcessor();
        processors.put(CONTENT_TYPE,jsonEncodeProcessor);
    }

    @Override
    public void encode(Object o, Type type, RequestTemplate requestTemplate) throws EncodeException {
        String contentType = getContentType(requestTemplate);
        processors.get(contentType).encoder(o, type, requestTemplate);

    }

    private String getContentType(RequestTemplate requestTemplate){
        Iterator<Map.Entry<String, Collection<String>>> iterator = requestTemplate.headers().entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<String, Collection<String>> entry = iterator.next();
            if(!CollectionUtils.isEmpty(entry.getValue()) && entry.getKey().equals("Content-Type")){
                Iterator<String> values = entry.getValue().iterator();
                while (values.hasNext()){
                    String next = values.next();
                    if(!StringUtils.isEmpty(next)){
                        return new String(next.getBytes());
                    }
                }
            }
        }
        return CONTENT_TYPE;
    }
}
