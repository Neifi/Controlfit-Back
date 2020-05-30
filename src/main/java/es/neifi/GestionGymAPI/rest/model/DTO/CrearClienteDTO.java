package es.neifi.GestionGymAPI.rest.model.DTO;

import java.util.List;

import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorario;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class CrearClienteDTO {
	private int id_gimnasio;
	private String dni;
	private String password;
	private String nombre;
	private String apellidos;
	private String fecha_nacimiento;
	private String fecha_inscripcion;

}
