package es.neifi.controlfit.security.jwt.model;

import java.util.Set;

import es.neifi.controlfit.cliente.dto.ClientDto;
import es.neifi.controlfit.rol.model.Rol;
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
public class JwtUserResponse extends ClientDto{
	private String token;
	
	@Builder(builderMethodName = "jwtUserResponseBuilder")
	public JwtUserResponse(Set<String>rol, String token) {
		
		this.token = token;
	}
}


