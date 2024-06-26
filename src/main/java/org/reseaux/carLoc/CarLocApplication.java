package org.reseaux.carLoc;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
public class CarLocApplication {

	public static void main(String[] args) {
        // Récupération du port défini par Heroku via la variable d'environnement PORT
        String port = System.getenv("PORT");
        if (port == null || port.isEmpty()) {
            port = "9000"; // Utilisation d'un port par défaut si PORT n'est pas défini
        }

        // Configuration du port pour Spring Boot
        System.setProperty("server.port", port);
        SpringApplication.run(CarLocApplication.class, args);
	}

}
