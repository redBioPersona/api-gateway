package red.biopersona.apigateway;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
/***
 * Clase que inicia el GTW spring cloud gateway
 * 
 * @author Omar Barrera Valentin
 */
public class ApiGatewayApplication {


	/***
	 * Nombre de la aplicacion
	 */
	@Value("${spring.application.name:api-gateway}")
	private String appName;
	
	
	public static void main(String[] args) {
		SpringApplication.run(ApiGatewayApplication.class, args);
	}

}
