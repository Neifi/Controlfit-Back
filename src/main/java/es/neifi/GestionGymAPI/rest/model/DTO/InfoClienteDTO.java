package es.neifi.GestionGymAPI.rest.model.DTO;

import java.util.List;

import javax.persistence.Basic;

import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class InfoClienteDTO {
	
	private String dni;
	private String nombre;
	private String apellidos;
	private String fecha_nacimiento;
	private String email;
	private String calle;
	private String codigo_postal;
	private String ciudad;
	private String provincia;

	private String fecha_inscripcion;

	
	//private List<RegistroHorario> registrohorario;

	
}
