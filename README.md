# homer
## homer简介
- 一款分布式事件框架。
- 服务A只需要关注事件发布，不需要关注将事件发布给谁。服务B只关注监听某种类型事件，不需要关注由谁发布事件，实现事件发布者和监听者的解耦。

## homer原理
- 将spring原生事件机制中的listener注册到RequestMappingHandlerMapping中，事件发布者发布事件时，通过事件类型找到zookeeper中注册的listener，然后执行http调用。

## homer使用
- 见example
```
// consumer服务publish event
    @Autowired
    private GenericHomerContext genericHomerContext;

    @RequestMapping("ok")
    public String ok() throws Exception {
        EventDemo demo = new EventDemo(this, "okk");
        genericHomerContext.publishEvent(demo);
        return "ok";
    }
```

```
// provider服务接收event
@PublicListenner(scope = "scope1")
public class EventDemoListener implements ApplicationListener<EventDemo> {

    @Override
    public void onApplicationEvent(EventDemo event) {
        System.out.println("receiver " + event.getMessage());
    }

    public String requestMappingHandlerMapping(EventDemo event ){
        return "requestMappingHandlerMapping";
    }

}
```