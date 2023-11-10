package es.neifi.controlfit;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import es.neifi.controlfit.customer.storage.StorageService;

@SpringBootApplication
@EnableJpaAuditing
public class ControlFitApplication {

	public static void main(String[] args) {
		SpringApplication.run(ControlFitApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner init(StorageService storageService) {
		
		return args -> {
			//TODO servicio en la nube
			storageService.deleteAll();
			storageService.init();
		};
	}

}
