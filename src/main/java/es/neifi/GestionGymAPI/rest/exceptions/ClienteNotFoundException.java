package es.neifi.GestionGymAPI.rest.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class ClienteNotFoundException extends RuntimeException{

	private static final long serialVersionUID = -4672199995633832565L;
	
	public ClienteNotFoundException(int id) {
		super("No se ha encontrado al cliente con la id "+id);
	}
}
