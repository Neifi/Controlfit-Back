package es.neifi.GestionGymAPI.rest.model.DTO;

import es.neifi.GestionGymAPI.rest.model.rol.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CrearUsuarioDTO {
	private int id;
	private String username;
	private String avatar;
	private String password;
	//private Rol rol;
	
}
