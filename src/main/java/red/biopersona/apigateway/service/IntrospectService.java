package red.biopersona.apigateway.service;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestOperations;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.apigateway.model.ErrorHandlerDTO;

/***
 * clase que consume el servicio de Instrospect para validar que el access token
 * enviado se encuentra ok
 * 
 * @author Omar Barrera Valentin
 */
@Slf4j
@Service
public class IntrospectService implements IIntrospectService {

	/***
	 * Url del endPoint instrospect para aplicaciones internas
	 */
	@Value("${urlClient}")
	private String urlClient;


	/***
	 * RestOperations de tipo template
	 */
	@Autowired
	private RestOperations oauthServerTemplate;


	/***
	 * Metodo que permite el consumo del servicio Introspect
	 * @param Jwt jwt a inyectar en la peticion, el cual fue firmado por la llave privada
	 * @param AccessToken token de acceso, proporcionado por el cliente consumidor
	 * @param Scope scope del cliente
	 * @param tipoAccessToken prefijo del accessToken, permite validar si es persona fisica o app interna
	 */
	@Override
	public Object callInstrospect(String client, String AccessToken) {

		try {
			HttpHeaders headers = new HttpHeaders();
			headers.add("Content_Type", "application/x-www-form-urlencoded");
			headers.add(AUTHORIZATION, "Bearer " + AccessToken);
			headers.add("need-jwt", "yes");

			LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<String, Object>();
			body.add("token", AccessToken);
			body.add("token_type_hint", "access_token");
			body.add("key", client);

			HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<LinkedMultiValueMap<String, Object>>(
					body, headers);
			log.info("Invocando al instrospect " + urlClient + " client " + client);
			ResponseEntity<?> response = oauthServerTemplate.postForEntity(
					urlClient,
					requestEntity,
					String.class,
					String.class);

			log.info("Post_check:token response: " + response.getStatusCodeValue() + " " + response.getBody().toString());
			if(response.getBody().toString().equalsIgnoreCase("false")) {
				ErrorHandlerDTO abc = new ErrorHandlerDTO();
				abc.setCause("invalid_client");
				abc.setMessage("invalid_client");
				return abc;
			}else {
				return response.getBody().toString();	
			}
		} catch (HttpClientErrorException | HttpServerErrorException ex) {
			log.error("Error contacting with oauth server", ex);
			ErrorHandlerDTO abc = new ErrorHandlerDTO();
			abc.setCause("instrospect_" + ex.getRawStatusCode() + "_communication_error");
			abc.setMessage(ex.getMessage() + " - " + ex.getStatusText() + " - " + ex.getResponseBodyAsString());
			return abc;
		} catch (RestClientException e) {
			log.error("Error contacting with oauth server", e);
			ErrorHandlerDTO abc = new ErrorHandlerDTO();
			abc.setCause("instrospect_communication_error");
			abc.setMessage(e.getMessage());
			return abc;
		}
	}
}
