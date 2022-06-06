package red.biopersona.apigateway.service;
import static org.springframework.http.HttpHeaders.AUTHORIZATION;

import java.nio.charset.Charset;
import java.util.Objects;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.Base64Utils;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;

/***
 * Clase que valida el access token
 * @author Omar Barrera Valentin
 */
@Service
@Slf4j
public class ValidateAccessTokenService implements IValidateAccessTokenService {
	/**
	 * ClientId del scope
	 */
	@Value("${headerClientId:client}")
	private String ClientId;
	
	/***
	 * Metodo que valida el header
	 * @param exchange ServerWebExchange
	 * @return authorization code
	 */
	public String getAccessTokenFromRequest(ServerWebExchange exchange) {		
		String authorization = exchange.getRequest().getHeaders().getFirst(AUTHORIZATION);
		String result=authorization != null ? authorization.replace("Bearer ", "") : null;
		if (Objects.isNull(result)|| result.isEmpty()) {
			log.error(AUTHORIZATION+" header no existe");
		}
		return result; 
	}
	
	/***
	 * Metodo que obtiene el scope del header
	 * @param peticion
	 * @return scope almacenado en el yaml
	 */
	public String getIssFromRequest(ServerWebExchange exchange) {
		return exchange.getRequest().getHeaders().getFirst("iss");
	}
	
	/***
	 * String get prefijo del accessToke
	 * @param accessToken String del access token
	 */
	public String tipoAccessToken(String accessToken) {
		String result=null;
	        if(accessToken!=null && accessToken.length()>1){
	            String prefix=accessToken.substring(0,2);
	            switch(prefix){
	                case "IN":
	                case "PF":
	                	result=prefix;
	                    break;
	                default:
	                	log.error("El prefijo: "+prefix+" en el accessToken, es desconocido");
	                break;
	            }
	        }else{
	            log.error("Error prefijo en accessToken, desconocido");
	        }
	        return result;
	}

	/***
	 * Metodo que valida el header
	 * @param exchange ServerWebExchange
	 * @return authorization code
	 */
	public String getClientIdFromRequest(ServerWebExchange exchange) {
		String result=exchange.getRequest().getHeaders().getFirst(ClientId);
		if (Objects.isNull(result)|| result.isEmpty()) {
			log.error(ClientId+" no existe");
		}
		return result;
	}
	

	/***
	 * Metodo que asigna los datos para el aUTH
	 * @param username username
	 * @param password password
	 * @return Basic Header
	 */
	public String createBasicAuthHeader(String username, String password) {
		String auth = username + ":" + password;
		byte[] encodedAuth = Base64Utils.encode(auth.getBytes(Charset.forName("UTF-8")));
		return "Basic " + new String(encodedAuth);
	}

}
