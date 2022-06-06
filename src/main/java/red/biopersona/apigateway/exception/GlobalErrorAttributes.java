package red.biopersona.apigateway.exception;

import java.net.UnknownHostException;
import java.util.Map;

import org.springframework.boot.web.error.ErrorAttributeOptions;
import org.springframework.boot.web.reactive.error.DefaultErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.ServerRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

/***
 * Clase que mapea las excepciones en el consumo del servicio
 * @author Omar Barrera Valentin
 */
@Component
@Slf4j
@Getter
@Setter
public class GlobalErrorAttributes extends DefaultErrorAttributes {

	
	/**
	 * Estatus de la peticion, en caso de la excepcion
	 */
	private HttpStatus status = HttpStatus.UNAUTHORIZED;
	
	/***
	 * Mensaje de error de la excepcion
	 */
	private String message = "Error";

	/***
	 * Cuerpo de la peticion, en caso de error
	 */
	@Override
	public Map<String, Object> getErrorAttributes(ServerRequest request, ErrorAttributeOptions options) {
	    Throwable error = getError(request);
	    log.error(error.getMessage()+" - "+error.getCause()+" - "+error.toString()+" - "+error.getClass().getName());
		Map<String, Object> map = super.getErrorAttributes(request, options);
		map.put("status", getStatus());
    	map.put("message", getMessage());	
	    if (error instanceof UnknownHostException) {
			map.put("error", error.getClass().getName());	
	    	map.put("message", error.toString());	
	    } 
		return map;
	}
}
