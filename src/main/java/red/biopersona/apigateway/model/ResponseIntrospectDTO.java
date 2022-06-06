package red.biopersona.apigateway.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Clase que mapea la respuesta del Introspect
 * @author Omar Barrera Valentin
 */
@Getter
@Setter
@ToString
public class ResponseIntrospectDTO implements Serializable{
	/**
	 * serialVersionUID
	 */
	private static final long serialVersionUID = 1L;
	
	/**
	 * jwt, respuesta del introspect
	 */
	private String jwt;
}
