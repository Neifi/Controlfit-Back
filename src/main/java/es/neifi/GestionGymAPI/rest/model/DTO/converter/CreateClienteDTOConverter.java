package es.neifi.GestionGymAPI.rest.model.DTO.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import es.neifi.GestionGymAPI.rest.model.DTO.CrearClienteDTO;
import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class CreateClienteDTOConverter {
	private final ModelMapper modelMapper;
	public Cliente convertToClient(CrearClienteDTO dto) {
		return  modelMapper.map(dto, Cliente.class);
		 
	}
	
	public CrearClienteDTO convertToDTO(Cliente cliente) {
		return modelMapper.map(cliente, CrearClienteDTO.class);
	}
}
