package es.neifi.controlfit.cliente.dto;

import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import es.neifi.controlfit.cliente.model.Cliente;
import es.neifi.controlfit.rol.model.Rol;
import es.neifi.controlfit.security.jwt.model.JwtUserResponse;

@Component
public class ClientDtoConverter {
	private ModelMapper modelMapper;
	
	public Cliente convertToEntity(TableListInfoDTO dto) {
		return modelMapper.map(dto,Cliente.class);
	}
	public Cliente convertToEntity(ClientDto dto) {
		return modelMapper.map(dto,Cliente.class);
	}
	
	public TableListInfoDTO convertToDto(Cliente cliente) {
		return modelMapper.map(cliente,TableListInfoDTO.class);
	}
	
	public Object convertUserAndTokenToJwtUserResponse(Cliente cliente, String token) {
		return JwtUserResponse.jwtUserResponseBuilder()
				.rol(cliente.getRol().stream().map(Rol::name).collect(Collectors.toSet()))
				.token(token)
				.build();
	}


}	

