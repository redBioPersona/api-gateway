package red.biopersona.apigateway.service;

import java.nio.charset.StandardCharsets;

import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ServerWebExchange;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.extern.slf4j.Slf4j;
import red.biopersona.apigateway.model.ErrorHandlerDTO;
import reactor.core.publisher.Mono;

/***
 * Clase que mapea el error
 * @author Omar Barrera Valentin
 */
@Slf4j
@Service
public class CustomErrorService implements ICustomErrorService{
	
	/****
	 * Metodo con el error de la Respuesta
	 * @param exchange Peticion
	 * @param dataErrorResponsex Tipo de Error
	 * @return Vista con error
	 */
	public Mono<Void> errorResponse(ServerWebExchange exchange, ErrorHandlerDTO dataErrorResponsex) {
		log.info("unauthorized reason:{}", dataErrorResponsex.getMessage() + ":" + dataErrorResponsex.getCause());
		ServerHttpResponse response = exchange.getResponse();
		response.setStatusCode(HttpStatus.UNAUTHORIZED);
		response.getHeaders().add("Content-Type", "application/json");
		ObjectMapper objectMapper = new ObjectMapper();
		String dataMsj = "";
		try {
			dataMsj = objectMapper.writeValueAsString(dataErrorResponsex);
		} catch (JsonProcessingException e) {
			log.error("JsonProcessingException", e);
		}
		byte[] bytes = dataMsj.toString().getBytes(StandardCharsets.UTF_8);
		DataBuffer buffer = response.bufferFactory().wrap(bytes);
		return response.writeWith(Mono.just(buffer)).doOnNext(nil -> {
			response.setComplete();
		});
	}
}
