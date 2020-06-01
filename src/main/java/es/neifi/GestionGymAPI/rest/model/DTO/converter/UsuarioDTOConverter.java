package es.neifi.GestionGymAPI.rest.model.DTO.converter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import es.neifi.GestionGymAPI.rest.model.DTO.usuario.GetUserDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.usuario.PutUsuarioDTO;
import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorario;
import es.neifi.GestionGymAPI.rest.model.rol.Rol;
import es.neifi.GestionGymAPI.rest.model.usuario.Usuario;
import es.neifi.GestionGymAPI.rest.security.jwt.model.JwtUserResponse;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
/**
 * Utilizando Model Mapper convierte los datos del DTO en la clase(Entidad) asignada
 * @author neifi
 *
 */
public class UsuarioDTOConverter {

	private final ModelMapper modelMapper;
	private final PasswordEncoder encoder;

	public GetUserDTO convertUserToGetUserDTO(Usuario usuario) {
		System.out.println(usuario.getPassword());
		return GetUserDTO.builder()
				.username(usuario.getUsername())
				.avatar(usuario.getAvatar())
				
				.password(usuario.getPassword())
				.roles(usuario.getRol().stream().map(Rol::name).collect(Collectors.toSet()))
				.build();
			
				
	}
	
	public Object convertUserAndTokenToJwtUserResponse(Usuario usuario, String token) {
		// TODO Auto-generated method stub
		return JwtUserResponse.jwtUserResponseBuilder()
				.username(usuario.getUsername())
				.password(usuario.getPassword())
				.avatar(usuario.getAvatar())
				.roles(usuario.getRol().stream().map(Rol::name).collect(Collectors.toSet()))
				.token(token)
				.build();
	}
	
	public Usuario convertToUsuario(PutUsuarioDTO usuarioDTO) {
		return modelMapper.map(usuarioDTO,Usuario.class);
	}
}
