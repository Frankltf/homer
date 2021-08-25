package homer.event.bus.codec;

import com.alibaba.fastjson.JSON;
import feign.RequestTemplate;

import java.lang.reflect.Type;

/**
 * @Intro
 * @Author liutengfei
 */
public class JsonEncodeProcessor implements EncoderProcessor {
    public static final String CONTENT_TYPE = "application/json";
    @Override
    public void encoder(Object o, Type type, RequestTemplate template) {
        String jsonBody = JSON.toJSONString(o);
        template.header("Content-Type", new String[]{"application/json"});
        template.body(jsonBody);
    }


}
