package homer.event.bus.codec;

import com.alibaba.fastjson.JSONObject;
import feign.QueryMapEncoder;

import java.util.Map;

/**
 * @Intro
 * @Author liutengfei
 */
public class FeignQueryMapEncoder implements QueryMapEncoder {
    @Override
    public Map<String, Object> encode(Object o) {
        Map<String, Object> map = JSONObject.parseObject(JSONObject.toJSONString(o), Map.class);
        map.put("source","fefefefe");
        return map;
    }
}
