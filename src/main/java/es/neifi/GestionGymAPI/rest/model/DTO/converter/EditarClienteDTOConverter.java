package es.neifi.GestionGymAPI.rest.model.DTO.converter;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import es.neifi.GestionGymAPI.rest.model.DTO.CrearClienteDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.EditarClienteDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.usuario.DatosAccesoDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.usuario.DatosContactoDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.usuario.DatosPersonalesDTO;
import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EditarClienteDTOConverter {
	private final ModelMapper modelMapper;
	public Cliente convertToClient(EditarClienteDTO dto) {
		return  modelMapper.map(dto, Cliente.class); 
	}
	public Cliente convertToClient(DatosPersonalesDTO dto) {
		return  modelMapper.map(dto, Cliente.class); 
	}
	public Cliente convertToClient(DatosAccesoDTO dto) {
		return  modelMapper.map(dto, Cliente.class); 
	}
	public Cliente convertToClient(DatosContactoDTO dto) {
		return  modelMapper.map(dto, Cliente.class); 
	}
	
	
}	
