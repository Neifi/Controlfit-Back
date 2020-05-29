package es.neifi.GestionGymAPI.rest.model.DTO.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import es.neifi.GestionGymAPI.rest.model.DTO.InfoClienteDTO;
import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import lombok.RequiredArgsConstructor;

@Component 
@RequiredArgsConstructor
public class ClientDetailsDTOConverter {
	private final ModelMapper modelMapper;
	public InfoClienteDTO convertToDTO(Cliente cliente) {
		return modelMapper.map(cliente, InfoClienteDTO.class);
	}
	
}
