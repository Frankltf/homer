package homer.event.bus.annotation;

import homer.event.bus.spring.HomerComponentScanRegistrar;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * @Intro
 * @Author liutengfei
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@Import(HomerComponentScanRegistrar.class)
public @interface EnableHomer {
    String[] scopes() default {};
}
