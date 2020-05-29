package es.neifi.GestionGymAPI.rest.config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class Config {
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
//	BASIC AUTH CORS	
//	public WebMvcConfigurer corsConfig() {
//		
//		return new WebMvcConfigurer() {
//
//			@Override
//			public void addCorsMappings(CorsRegistry registry) {
//				registry.addMapping("/**"); // TODO Cambiar en produccion
//				/*
//				 * registry.addMapping(/usuario)
//				 * .allowedOrigins("http://localhost:4200)
//				 * .allowedMethods("GET","POST","PUT","DELETE")
//				 * .maxAge(3600);
//				 */
//				
//			}
//			
//		};
		
//	}
}
