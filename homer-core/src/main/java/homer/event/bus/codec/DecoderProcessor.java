package homer.event.bus.codec;

import feign.Response;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Intro
 * @Author liutengfei
 */
public interface DecoderProcessor {
    Object decode(Response response, Type type) throws IOException;
}
