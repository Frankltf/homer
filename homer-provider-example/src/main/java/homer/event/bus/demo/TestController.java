package homer.event.bus.demo;

import homer.event.bus.core.GenericHomerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;



/**
 * @Intro
 * @Author liutengfei
 */
@RestController
public class TestController {

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private ApplicationContext applicationEventPublisher;

    @Autowired
    private GenericHomerContext genericHomerContext;

    @RequestMapping("ok")
    public void ok() throws Exception {
//        EventDemo demo = new EventDemo(this, "okk");
//        applicationEventPublisher.publishEvent(demo);

        return ;
    }





}
