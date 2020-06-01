package es.neifi.GestionGymAPI.rest.security.jwt.model;

import java.util.Set;

import es.neifi.GestionGymAPI.rest.model.DTO.usuario.GetUserDTO;
import es.neifi.GestionGymAPI.rest.model.rol.Rol;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
/**
 * Al generarse el token envia los datos del usuario logeado y el token
 * @author neifi
 *
 */
public class JwtUserResponse extends GetUserDTO{
	private String token;
	
	@Builder(builderMethodName = "jwtUserResponseBuilder")
	public JwtUserResponse(String username, String password,String avatar, Set<String>roles, String token) {
		super(username,password,avatar,roles);
		this.token = token;
	}
}


