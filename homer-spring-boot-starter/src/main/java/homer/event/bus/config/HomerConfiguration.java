package homer.event.bus.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Intro
 * @Author liutengfei
 */
@Configuration
public class HomerConfiguration {

    @Bean
    public FilterRegistrationBean filterRegistrationBean(){
        FilterRegistrationBean<FullTraceServletFilter> registration = new FilterRegistrationBean<>();
        registration.setFilter(new FullTraceServletFilter());
        registration.addUrlPatterns("/*");
        registration.setName(FullTraceServletFilter.class.getName());
        return registration;
    }
}
