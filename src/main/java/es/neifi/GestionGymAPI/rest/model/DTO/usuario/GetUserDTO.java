package es.neifi.GestionGymAPI.rest.model.DTO.usuario;

import java.util.List;
import java.util.Set;

import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorario;
import es.neifi.GestionGymAPI.rest.model.usuario.Usuario;
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
public class GetUserDTO {
	private Usuario cliente;
	private String username;
	private String password;
	private String avatar;
	private Set<String>roles;
	private List<RegistroHorario> registroHorario;
	
	public GetUserDTO(String username, String password, String avatar, Set<String> roles) {
		super();
		this.username = username;
		this.password = password;
		this.avatar = avatar;
		this.roles = roles;
	}
}
