package es.neifi.GestionGymAPI.rest.model.DTO;

import java.util.List;

import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class PutClienteDTO {
	
	private int id;
	private String dni;
	private String calle;
	private String ciudad;
	private String provincia;
	private String codigoPostal;
	private String email;
	
}
