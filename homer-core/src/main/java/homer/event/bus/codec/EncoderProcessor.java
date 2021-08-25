package homer.event.bus.codec;

import feign.RequestTemplate;

import java.lang.reflect.Type;

/**
 * @Intro
 * @Author liutengfei
 */
public interface EncoderProcessor {
    void encoder(Object o, Type type, RequestTemplate requestTemplate);
}
