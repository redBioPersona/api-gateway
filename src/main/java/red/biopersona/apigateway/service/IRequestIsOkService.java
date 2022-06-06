package red.biopersona.apigateway.service;

import org.springframework.web.server.ServerWebExchange;

import red.biopersona.apigateway.model.ErrorHandlerDTO;


/**
 * 
 * @author Omar Barrera Valentin
 */
public interface IRequestIsOkService {
	/***
	 * Metodo que valida si la peticion se encuentra formada de manera correcta
	 * 
	 * @param exchange Peticion
	 * @return ErrorHandler
	 */
	ErrorHandlerDTO requestIsOk(ServerWebExchange exchange);
}
