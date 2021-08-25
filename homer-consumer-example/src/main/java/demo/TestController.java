package demo;

import homer.event.bus.core.GenericHomerContext;
import homer.event.bus.demo.EventDemo;
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
    private GenericHomerContext genericHomerContext;

    @RequestMapping("ok")
    public String ok() throws Exception {
        EventDemo demo = new EventDemo(this, "okk");
        genericHomerContext.publishEvent(demo);
        return "ok";
    }





}
