package red.biopersona.apigateway.model;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

import org.springframework.util.MultiValueMap;

import lombok.Getter;
import lombok.Setter;


/***
 * Clase con los datos del Jwt
 * 
 * @author Omar Barrera Valentin
 */
@Getter
@Setter
public class AuditModelDTO implements Serializable {

	/**
	 * UID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Subject, que sera almacenado en los datos a Auditar
	 */
	private String subject;
	/**
	 * clientId, que sera almacenado en los datos a Auditar
	 */
	private String clientId;
	/**
	 * api, que sera almacenado en los datos a Auditar
	 */
	private String api;
	/**
	 * path, que sera almacenado en los datos a Auditar
	 */
	private String path;
	/**
	 * remoteAddress, que sera almacenado en los datos a Auditar
	 */
	private String remoteAddress;
	/**
	 * Headers, que sera almacenado en los datos a Auditar
	 */
	private Map<String, List<String>> headers;
	/**
	 * Metodo, que sera almacenado en los datos a Auditar
	 */
	private String metod;
	/**
	 * queryParams, que sera almacenado en los datos a Auditar
	 */
	private MultiValueMap<String, String> queryParams;
}
