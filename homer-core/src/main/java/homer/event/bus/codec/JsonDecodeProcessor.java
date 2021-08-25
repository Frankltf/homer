package homer.event.bus.codec;

import com.alibaba.fastjson.JSON;
import feign.Response;
import feign.Util;
import org.apache.commons.io.IOUtils;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;

/**
 * @Intro
 * @Author liutengfei
 */
public class JsonDecodeProcessor implements DecoderProcessor {

    @Override
    public Object decode(Response response, Type type) throws IOException {
        if (response.status() == 404) {
            return Util.emptyValueOf(type);
        } else if (response.body() == null) {
            return null;
        } else {
            InputStream reader = response.body().asInputStream();
            byte[] bytes = IOUtils.toByteArray(new BufferedInputStream(reader));
            String s = new String(bytes, "utf-8");
            try {
                return s;
            } catch (Exception e) {
                throw e;
            } finally {
                Util.ensureClosed(reader);
            }
        }
    }
}
