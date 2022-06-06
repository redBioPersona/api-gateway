package red.biopersona.apigateway.service;

/***
 * clase que consume el servicio de Instrospect para validar
 * que el access token enviado se encuentra ok
 * @author Omar Barrera Valentin
 */
public interface IIntrospectService {
	/***
	 * Metodo que permite el consumo del servicio Introspect
	 * @param Jwt jwt a inyectar en la peticion, el cual fue firmado por la llave privada
	 * @param AccessToken token de acceso, proporcionado por el cliente consumidor
	 * @param Scope scope del cliente
	 * @param tipoAccessToken prefijo del accessToken, permite validar si es persona fisica o app interna
	 */
	public Object callInstrospect(String cliente, String AccessToken);
}
