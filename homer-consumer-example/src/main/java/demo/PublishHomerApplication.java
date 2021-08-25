package demo;

import homer.event.bus.annotation.EnableHomer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableHomer(scopes = {"scope1","scope2"} )
public class PublishHomerApplication {

	public static void main(String[] args) {
		SpringApplication.run(PublishHomerApplication.class, args);
	}

}

