package red.biopersona.apigateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestOperations;
import org.springframework.web.client.RestTemplate;

/***
 * Clase que configura el RestTemplate
 * 
 * @author Omar Barrera Valentin
 */
@Configuration
public class OAuthConfig {

	/***
	 * Metodo que retorna el RestTemplate
	 * 
	 * @return restTemplate
	 */
	@Bean
	public RestOperations oauthServerTemplate() {
		return new RestTemplate();
	}
}