package es.neifi.controlfit.cliente.service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import static java.util.stream.Collectors.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;

import org.springframework.stereotype.Service;

import org.springframework.web.server.ResponseStatusException;

import es.neifi.controlfit.cliente.dto.ClientDto;
import es.neifi.controlfit.cliente.dto.ClienteDtoConverter;
import es.neifi.controlfit.cliente.model.Cliente;
import es.neifi.controlfit.cliente.repo.ClienteRepository;
import es.neifi.controlfit.services.BaseService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@Service
@NoArgsConstructor
public class ClienteService extends BaseService<Cliente, Integer, ClienteRepository> {
	
	@Autowired
	private  ClienteRepository clienteRepository;
	@Autowired
	private  ClienteDtoConverter clienteDtoConverter;
	

	
	public Optional<Cliente> searchByUserName(String username) {
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
	
	public Cliente nuevoCliente(ClientDto cliente) {
		Cliente nuevoCliente = clienteDtoConverter.convertToEntity(cliente);
		try {

			return save(nuevoCliente);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de usuario existente");
		}
	}

	public Cliente editarCliente(ClientDto cliente, int id) {
		Cliente nuevoCliente = clienteDtoConverter.convertToEntity(cliente);
		try {
			buscarPorId(id);
			return save(nuevoCliente);
		} catch (DataIntegrityViolationException e) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Nombre de usuario existente");
		}
	}

	public void borrarPorId(int id) {
		try {
			buscarPorId(id);
			deleteById(id);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}

	public Optional<Cliente> buscarPorId(int id) {

		return findById(id);
	}

	public List<Cliente> listarClientes() {
		return findAll();
	}

}
