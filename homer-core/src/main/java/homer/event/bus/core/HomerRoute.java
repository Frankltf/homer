package homer.event.bus.core;

import homer.event.bus.model.IpHomer;

import java.util.List;

/**
 * @Intro
 * @Author liutengfei
 */
public interface HomerRoute {
    IpHomer getIpHomer(List<IpHomer> ipHomers);
}
