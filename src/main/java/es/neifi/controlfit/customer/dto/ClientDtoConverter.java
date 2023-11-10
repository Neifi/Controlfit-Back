package es.neifi.controlfit.customer.dto;

import es.neifi.controlfit.customer.model.Customer;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ClientDtoConverter {
	private ModelMapper modelMapper;
	
	public Customer convertToEntity(TableListInfoDTO dto) {
		return modelMapper.map(dto, Customer.class);
	}
	public Customer convertToEntity(ClientDto dto) {
		return modelMapper.map(dto, Customer.class);
	}
	
	public TableListInfoDTO convertToDto(Customer customer) {
		return modelMapper.map(customer,TableListInfoDTO.class);
	}



}	

