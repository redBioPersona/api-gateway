package red.biopersona.apigateway.service;
import org.springframework.web.server.ServerWebExchange;

import red.biopersona.apigateway.model.ErrorHandlerDTO;
import reactor.core.publisher.Mono;

/***
 * Clase que mapea el error
 * @author Omar Barrera Valentin
 */
public interface ICustomErrorService {
	/****
	 * Metodo con el error de la Respuesta
	 * @param exchange Peticion
	 * @param dataErrorResponsex Tipo de Error
	 * @return Vista con error
	 */
	Mono<Void> errorResponse(ServerWebExchange exchange, ErrorHandlerDTO dataErrorResponsex);
}
