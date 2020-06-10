package es.neifi.GestionGymAPI.rest.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger.web.ApiKeyVehicle;
import springfox.documentation.swagger.web.SecurityConfiguration;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {
	
	@Bean
	public SecurityConfiguration security() {
	    final String token = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJ1c2VybmFtZSI6InN3YWdnZXIiLCJwYXNzd29yZCI6InN3YWdnZXIiLCJpYXQiOjQ3NDY2NDMyMDB9.9jE7wXKfXs0xE48GmBVn_OahwDiFQA063Nt5YN-cjdQ";
	    return new SecurityConfiguration(null, null, null, null, "Bearer " + token, ApiKeyVehicle.HEADER, "Authorization", ",");
	}
	
	@Bean
	public Docket api( ) {

		return new Docket(DocumentationType.SWAGGER_2)
				.pathMapping("/")
				.select()
				.apis(RequestHandlerSelectors.basePackage("es.neifi.GestionGymAPI.rest.controller"))
				.paths(PathSelectors.any())
				.build()
				
				.apiInfo(apiInfo())
				;
	}
	
	@Bean
	public ApiInfo apiInfo() {
		return new ApiInfoBuilder()
				.title("GestionGym API")
				.description("API para la gestion de clientes y usuarios de un gimnasio")
				.version("1.0")
				.contact(new Contact("Neifi", "https://github/neifi", "neifialcantara@gmail.com"))
				.build();
	}
	
	
}
