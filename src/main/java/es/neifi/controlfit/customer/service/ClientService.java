package es.neifi.controlfit.customer.service;

import java.util.List;
import java.util.Optional;

import es.neifi.controlfit.customer.model.Customer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import es.neifi.controlfit.customer.dto.ClientDto;
import es.neifi.controlfit.customer.dto.ClientDtoConverter;
import es.neifi.controlfit.customer.repo.ClienteRepository;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ClientService {
	
	@Autowired
	private  ClienteRepository clienteRepository;
	@Autowired
	private ClientDtoConverter clientDtoConverter;
	

	
	public Optional<Customer> searchClientByUsername(String username) {
		return this.clienteRepository.findCustomerByUsername(username);
	}

	
	public Customer registerCustomer(ClientDto cliente) {
		Customer clientToBeRegistered = clientDtoConverter.convertToEntity(cliente);
		try {

			return clienteRepository.save(clientToBeRegistered);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de usuario existente");
		}
	}

	public Customer updateClientById(ClientDto cliente, int id) {
		Customer nuevoCustomer = clientDtoConverter.convertToEntity(cliente);
		try {
			findCustomerById(id);
			return clienteRepository.save(nuevoCustomer);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de usuario existente");
		}
	}

	public void deleteCustomer(int id) {
		try {
			findCustomerById(id);
			deleteCustomer(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Optional<Customer> findCustomerById(int id) {

		return clienteRepository.findById(id);
	}

	public List<Customer> findAllClientsOrderedById() {
		return clienteRepository.findAllByOrderByIdAsc();
	}


}
