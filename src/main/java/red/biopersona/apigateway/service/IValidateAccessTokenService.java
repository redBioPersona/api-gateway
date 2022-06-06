package red.biopersona.apigateway.service;

import org.springframework.web.server.ServerWebExchange;

/***
 * Clase que valida el access token
 * 
 * @author Omar Barrera Valentin
 */
public interface IValidateAccessTokenService {
	/***
	 * Metodo que valida el header
	 * 
	 * @param exchange ServerWebExchange
	 * @return authorization code
	 */
	public String getAccessTokenFromRequest(ServerWebExchange exchange);

	/***
	 * Metodo que valida el header
	 * 
	 * @param exchange ServerWebExchange
	 * @return authorization code
	 */
	public String getClientIdFromRequest(ServerWebExchange exchange);

	/***
	 * Metodo que obtiene el scope del header
	 * 
	 * @param peticion
	 * @return scope almacenado en el yaml
	 */
	public String getIssFromRequest(ServerWebExchange exchange);

	/***
	 * String get prefijo del accessToke
	 * 
	 * @param accessToken String del access token
	 */
	public String tipoAccessToken(String accessToken);
}
