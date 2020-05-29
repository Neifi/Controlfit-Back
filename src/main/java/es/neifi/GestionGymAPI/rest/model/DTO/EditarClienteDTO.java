package es.neifi.GestionGymAPI.rest.model.DTO;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EditarClienteDTO {
	
	private String dni;
	private String nombre;
	private String apellidos;
	private String fecha_nacimiento;
	private String fecha_inscripcion;
	private String email;
	private String calle;
	private int codigo_postal;
	private String ciudad;
	private String provincia;
	
}
