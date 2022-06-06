package red.biopersona.apigateway.filter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import reactor.core.publisher.Mono;
import red.biopersona.apigateway.model.ErrorHandlerDTO;
import red.biopersona.apigateway.service.*;

/***
 * Clase que valida las peticiones entrantes-salientes
 * 
 * @author Omar Barrera Valentin
 */
@Component
@Slf4j
public class PreFilter implements GlobalFilter {
	/** Instancia para usar la interface de IRequestIsOkService */
	@Autowired
	private IRequestIsOkService RequestIsOkService;

	/** Instancia para usar la interface de ICustomErrorService */
	@Autowired
	private ICustomErrorService customErrorService;

	/** Instancia para usar la interface de IIntrospectService */
	@Autowired
	private IIntrospectService introspectServ;

	/**
	 * Clase con la validacion del AccessToken
	 */
	@Autowired
	private IValidateAccessTokenService validaAccessToken;


	/**
	 * Clase para almacenar la auditoria
	 */
	@Autowired
	private IAuditService auditService;

	/***
	 * Metodo que valida la peticion que pasara al servicio destino
	 * 
	 * @param ServerWebExchange  peticion
	 * @param GatewayFilterChain peticion chain
	 */
	@Override
	public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
		ErrorHandlerDTO requestIsOk = RequestIsOkService.requestIsOk(exchange);
		if (requestIsOk != null) {
			log.info("Error, con la estructura del api gtw");
			return customErrorService.errorResponse(exchange, requestIsOk);
		}
		String cliente = validaAccessToken.getClientIdFromRequest(exchange);
		String accessToken = validaAccessToken.getAccessTokenFromRequest(exchange);

		Object responseIntrospect = introspectServ.callInstrospect(cliente, accessToken);
		if (responseIntrospect.getClass() == ErrorHandlerDTO.class) {
			// Hubo un error al llamar al introspect
			return customErrorService.errorResponse(exchange, (ErrorHandlerDTO) responseIntrospect);
		} 

		ServerHttpRequest mutatedRequest = exchange.getRequest().mutate()
				.header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
				.build();
		exchange = exchange.mutate().request(mutatedRequest).build();
		
		auditService.audit(exchange);
		return chain.filter(exchange);
	}
}