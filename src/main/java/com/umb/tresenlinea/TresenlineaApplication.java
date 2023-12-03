package com.umb.tresenlinea;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TresenlineaApplication {

	public static void main(String[] args) {
		// Obtén el puerto del entorno de Heroku o usa un valor predeterminado
		String port = System.getenv("PORT");
		if (port == null) {
			port = "8080"; // Puerto predeterminado si no se encuentra la variable PORT
		}

		// Configura el puerto
		System.setProperty("server.port", port);

		// Inicia la aplicación
		SpringApplication.run(TresenlineaApplication.class, args);
	}

}
