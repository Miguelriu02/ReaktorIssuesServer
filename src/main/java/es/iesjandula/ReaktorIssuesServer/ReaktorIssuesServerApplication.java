package es.iesjandula.ReaktorIssuesServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//Anotación que marca esta clase como la clase principal de la aplicación Spring Boot
@SpringBootApplication
//Escanea componentes dentro del paquete "es.iesjandula", lo que permite que Spring detecte y registre los beans definidos en ese paquete
@ComponentScan(basePackages = "es.iesjandula")
public class ReaktorIssuesServerApplication
{
	// El main es el punto de entrada de la aplicación, aquí es donde comienza la
	// ejecución.
	// SpringApplication.run() arranca el contexto de Spring, inicializando todos
	// los beans y configuraciones necesarias.
	public static void main(String[] args)
	{
		SpringApplication.run(ReaktorIssuesServerApplication.class, args);
	}

}
