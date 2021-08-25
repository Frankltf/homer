package homer.event.bus.codec;

import feign.FeignException;
import feign.Response;
import feign.codec.DecodeException;
import feign.codec.Decoder;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * @Intro
 * @Author liutengfei
 */
public class DefaultDecoder implements Decoder {
    private DecoderProcessor decodeProcessor;
    public DefaultDecoder() {
        JsonDecodeProcessor jsonDecodeProcessor = new JsonDecodeProcessor();
        this.decodeProcessor = jsonDecodeProcessor;
    }

    @Override
    public Object decode(Response response, Type type) throws IOException, DecodeException, FeignException {
        return decodeProcessor.decode(response,type);
    }
}
