package homer.event.bus.core;

import homer.event.bus.model.IpHomer;

import java.util.List;

/**
 * @Intro
 * @Author liutengfei
 */
public class PollHomerRoute implements HomerRoute {
    @Override
    public IpHomer getIpHomer(List<IpHomer> ipHomers) {
        return ipHomers.get(0);
    }
}
