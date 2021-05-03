package es.neifi.controlfit.cliente.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import es.neifi.controlfit.cliente.dto.ClientDto;
import es.neifi.controlfit.cliente.dto.ClientDtoConverter;
import es.neifi.controlfit.cliente.model.Cliente;
import es.neifi.controlfit.cliente.repo.ClienteRepository;
import es.neifi.controlfit.services.BaseService;
import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
public class ClientService extends BaseService<Cliente, Integer, ClienteRepository> {
	
	@Autowired
	private  ClienteRepository clienteRepository;
	@Autowired
	private ClientDtoConverter clientDtoConverter;
	

	
	public Optional<Cliente> searchClientByUsername(String username) {
		return this.clienteRepository.findByUsername(username);
	}
	
//	TODO public List<Cliente> searchByCriteria(String criteria){
//		
//		List<Cliente> clients = findAll();
//		
//
//		List <Cliente> findedClients = clients.parallelStream().filter(c -> c.getNombre().equals(criteria)
//				|| c.getNombre().contains(criteria)
//				|| c.getCalle().contains(criteria)
//				|| c.getCiudad().contains(criteria)
//				|| c.getFecha_nacimiento().contains(criteria)
//				|| c.getApellidos().contains(criteria)
//				|| c.getDni().contains(criteria)
//				|| c.getEmail().contains(criteria)).collect(toList());
//		
//		
//		return findedClients;
//	}
	
	public Cliente registerClient(ClientDto cliente) {
		Cliente clientToBeRegistered = clientDtoConverter.convertToEntity(cliente);
		try {

			return save(clientToBeRegistered);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de usuario existente");
		}
	}

	public Cliente updateClientById(ClientDto cliente, int id) {
		Cliente nuevoCliente = clientDtoConverter.convertToEntity(cliente);
		try {
			findClientById(id);
			return save(nuevoCliente);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de usuario existente");
		}
	}

	public void deleteClientById(int id) {
		try {
			findClientById(id);
			deleteClientById(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Optional<Cliente> findClientById(int id) {

		return findById(id);
	}

	public List<Cliente> findAllClientsOrderedById() {
		return clienteRepository.findAllByOrderByIdAsc();
	}


}
