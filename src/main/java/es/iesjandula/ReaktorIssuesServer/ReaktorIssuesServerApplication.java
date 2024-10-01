package es.iesjandula.ReaktorIssuesServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"es.iesjandula"})
public class ReaktorIssuesServerApplication {

	public static void main(String[] args) {
		SpringApplication.run(ReaktorIssuesServerApplication.class, args);
	}

}
