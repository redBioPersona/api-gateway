package red.biopersona.apigateway.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.apigateway.model.ErrorHandlerDTO;


/***
 * Clase que valida si la peticion se encuentra ok
 * @author Omar Barrera Valentin
 */
@Slf4j
@Service
public class RequestIsOkService implements IRequestIsOkService{
	/**
	 * Clase con la validacion del AccessToken
	 */
	@Autowired
	private IValidateAccessTokenService validaAccessToken;


	/***
	 * Metodo que valida si la peticion se encuentra formada de manera correcta
	 * @param exchange Peticion
	 * @return ErrorHandler
	 */
	public ErrorHandlerDTO requestIsOk(ServerWebExchange exchange) {
		String routeQuery=exchange.getRequest().getURI().getRawQuery();
		String routePath=exchange.getRequest().getURI().getPath();
		log.info("Filtrando la route "+routePath+" withQueryParams: "+routeQuery);
		
		ErrorHandlerDTO estado = null;
		String accessToken = validaAccessToken.getAccessTokenFromRequest(exchange);
		
		if (accessToken == null) {
			log.info("accessToken no definido");
			estado = new ErrorHandlerDTO();
			estado.setCause("no_access_token");
			estado.setMessage("no_access_token");
		}
		
		String clientId = validaAccessToken.getClientIdFromRequest(exchange);
		if (clientId == null) {
			estado = new ErrorHandlerDTO();
			estado.setCause("no_header_client");
			estado.setMessage("client");
		}

		return estado;
	}
	


}
