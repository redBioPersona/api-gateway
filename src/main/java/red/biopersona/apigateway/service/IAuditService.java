package red.biopersona.apigateway.service;
import org.springframework.web.server.ServerWebExchange;


/**
 * Interfaz para crear las pistas de auditoria
 * 
 * @author Omar Barrera Valentin
 */
public interface IAuditService {
	/***
	 * Metodo a auditar
	 * @param ServerWebExchange peticion a auditar
	 */
	public void audit(ServerWebExchange exchange, String userProfile);
	/***
	 * Metodo a auditar
	 * @param ServerWebExchange peticion a auditar
	 */
	public void audit(ServerWebExchange exchange);
}
