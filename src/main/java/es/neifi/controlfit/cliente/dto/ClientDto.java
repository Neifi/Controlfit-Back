package es.neifi.controlfit.cliente.dto;

import java.util.Set;

import es.neifi.controlfit.rol.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor @NoArgsConstructor
@Setter @Getter
public class ClientDto {
	private String dni;
	private String nombre;
	private String apellidos;
	private String fecha_nacimiento;
	private String email;
	private String calle;
	private String codigo_postal;
	private String ciudad;
	private String provincia;
	private String username;
	private String avatar;
	private Set<String> rol;
	
	public ClientDto(Set<String> rol) {
		this.rol = rol;
	}
}	

