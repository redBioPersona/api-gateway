package red.biopersona.apigateway.service;

import java.util.Arrays;
import java.util.Collections;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.apigateway.model.AuditModelDTO;

/**
 * implementa la extracción de pistas de auditoria
 * 
 * @author Omar Barrera Valentin
 */
@Service
@Slf4j
public class AuditService implements IAuditService {
	/**
	 * Datos del marker audit
	 */
	private static final Marker AUDIT_MARKER = MarkerFactory.getMarker("AUDIT");

	/***
	 * Nombre de la aplicacion
	 */
	@Value("${spring.application.name:api-gateway}")
	private String apiName;



	/***
	 * Metodo a auditar
	 * @param ServerWebExchange peticion a auditar
	 */
	@Override
	public void audit(ServerWebExchange exchange) {

	}

	/***
	 * Metodo a auditar
	 * @param ServerWebExchange peticion a auditar
	 */
	@Override
	public void audit(ServerWebExchange exchange, String subject) {
		log.info("ejecuta auditoria");
		AuditModelDTO audit = new AuditModelDTO();
		audit.setSubject(subject);
		audit.setClientId(exchange.getRequest().getHeaders().getFirst("x-client-id"));
		audit.setQueryParams(exchange.getRequest().getQueryParams());
		audit.setApi(apiName);
		/**
		 * ToDo obtener los headers
		 */
		audit.setHeaders(Collections.singletonMap("header-name", Arrays.asList("value-1", "value-2")));
		/**
		 * To-Do Obtener todos los atributos que define el modelo AuditModel
		 */
		/**
		 * Se debera implementar un mecanismo asincrono que envie los mensajes al
		 * cluster de RabbitMQ
		 * 
		 * El mecanismo asincrono debera tener un pool de hilos con
		 */
		log.trace(AUDIT_MARKER, "{}", audit);
	}

}