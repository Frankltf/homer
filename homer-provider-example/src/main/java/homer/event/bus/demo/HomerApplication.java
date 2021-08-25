package homer.event.bus.demo;

import homer.event.bus.annotation.EnableHomer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@EnableHomer(scopes = {"scope1","scope2"} )
public class HomerApplication {

	public static void main(String[] args)  {
		SpringApplication.run(HomerApplication.class, args);
	}
}

