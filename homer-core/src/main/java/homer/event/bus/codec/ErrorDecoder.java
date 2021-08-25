package homer.event.bus.codec;

import feign.FeignException;
import feign.Response;

import static feign.FeignException.errorStatus;

/**
 * @Intro
 * @Author liutengfei
 */
public class ErrorDecoder implements feign.codec.ErrorDecoder {
    @Override
    public Exception decode(String methodKey, Response response) {
        FeignException exception = errorStatus(methodKey, response);
        return exception;
    }
}
