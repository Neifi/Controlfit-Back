package es.neifi.GestionGymAPI.rest.exceptions;

import java.time.LocalDateTime;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.JsonMappingException;

@RestControllerAdvice
@Deprecated
public class GlobalControllerAdvice extends ResponseEntityExceptionHandler{
	
	@ExceptionHandler(ClienteNotFoundException.class)
	public ResponseEntity<ApiError> handleClienteNotFound(ClienteNotFoundException exception){
//		ApiError apiError = new ApiError();
//		apiError.setEstado(HttpStatus.NOT_FOUND);
//		apiError.setFecha(LocalDateTime.now());
//		apiError.setMsg(exception.getMessage());
//		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
		
		ApiError apiError = new ApiError(HttpStatus.NOT_FOUND, exception.getMessage());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
	}

	@Override
	protected ResponseEntity<Object> handleExceptionInternal(Exception ex, Object body, HttpHeaders headers,
			HttpStatus status, WebRequest request) {
		ApiError apiError = new ApiError(status, ex.getMessage());
		
		return ResponseEntity.status(status).headers(headers).body(apiError);
	}
	
}
