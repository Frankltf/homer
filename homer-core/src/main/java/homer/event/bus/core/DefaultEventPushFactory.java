package homer.event.bus.core;

import feign.Feign;
import feign.QueryMapEncoder;
import feign.codec.Decoder;
import feign.codec.Encoder;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @Intro
 * @Author liutengfei
 */
public class DefaultEventPushFactory implements EventPushFactory {

    @Autowired
    private QueryMapEncoder queryMapEncoder;
    @Autowired
    private Encoder encoder;
    @Autowired
    private Decoder decoder;
    @Autowired
    private feign.codec.ErrorDecoder errorDecoder;
    @Override
    public EventPush addEventPush(String listener, String address) {
        EventPush eventPush = Feign.builder()
                .queryMapEncoder(queryMapEncoder)
                .encoder(encoder)
                .decoder(decoder)
                .errorDecoder(errorDecoder)
                .target(EventPush.class, "http://" + address + "/" + listener);
        return eventPush;
    }
}
