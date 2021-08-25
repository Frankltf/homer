package homer.event.bus.core;

import homer.event.bus.model.IpHomer;

import java.util.List;
import java.util.Random;

/**
 * @Intro
 * @Author liutengfei
 */
public class RandomHomerRoute implements HomerRoute {
    private Random random = new Random();

    @Override
    public IpHomer getIpHomer(List<IpHomer> ipHomers) {

        int index = random.nextInt(ipHomers.size());
        return ipHomers.get(index);
    }
}