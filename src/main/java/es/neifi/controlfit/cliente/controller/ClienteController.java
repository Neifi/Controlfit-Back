package es.neifi.controlfit.cliente.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.http.*;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import es.neifi.controlfit.almacenamiento.StorageService;
import es.neifi.controlfit.cliente.dto.*;
import es.neifi.controlfit.cliente.exception.ClienteNotFoundException;
import es.neifi.controlfit.cliente.model.Cliente;
import es.neifi.controlfit.cliente.repo.ClienteRepository;
import es.neifi.controlfit.cliente.service.ClienteService;
import es.neifi.controlfit.ficheros.controller.FileController;
import es.neifi.controlfit.registrohorario.repo.TimeRegistrationRepository;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({ "/api" })

@RequiredArgsConstructor
public class ClienteController {

	private final ClienteRepository clienteRepository;

	private final TimeRegistrationRepository timeRegistrationRepository;
	private final ClienteService clientService;
	private final StorageService storageService;
	private final ClienteDtoConverter clienteDtoConverter;

	@PutMapping(value = "user/avatar", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> uploadAvatar(@AuthenticationPrincipal ClientDto clientDto,
			@RequestPart("file") MultipartFile file) {
		String url = "";
		if (!file.isEmpty()) {
			url = storeInLocal(file);
			clientDto.setAvatar(url);
			
			try {
				Optional<Cliente> clientEntity = searchClientByUsername(clientDto);

				return ResponseEntity.ok(clientService.editarCliente(clientDto, clientEntity.get().getId()));
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error uploading the image");
		}

	}

	public Optional<Cliente> searchClientByUsername(ClientDto currentClient) {
		Optional<Cliente> id = clientService.searchByUserName(currentClient.getUsername());
		return id;
	}

	private String storeInLocal(MultipartFile file) {
		String url;
		String image = storageService.store(file);
		url = MvcUriComponentsBuilder.fromMethodName(FileController.class, "serveFile", image, null).build()
				.toUriString();
		return url;
	}

	@GetMapping("/cliente")
	public ResponseEntity<?> obtenerTodos() {
		List<Cliente> clientes = clienteRepository.findAllByOrderByIdAsc();
		if (clientes.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
		} else {
			List<TableListInfoDTO> dtoList = convertDtoToList(clientes);
			return ResponseEntity.ok(dtoList);
		}

	}

	private List<TableListInfoDTO> convertDtoToList(List<Cliente> clientes) {
		List<TableListInfoDTO> dtoList = clientes.stream().map(clienteDtoConverter::convertToDto)
				.collect(Collectors.toList());
		return dtoList;
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> obtenerUno(@PathVariable int id) {
		try {

			return ResponseEntity.ok(clienteRepository.findById(id));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@GetMapping("/cliente/me")
	public ResponseEntity<?> obtenerMe(@AuthenticationPrincipal Cliente cliente) {
		try {

			return ResponseEntity.ok(clienteRepository.findById(cliente.getId()));

		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@PostMapping("/cliente")
	public ResponseEntity<Cliente> crearCliente(@RequestBody Cliente nuevo, @AuthenticationPrincipal Cliente admin) {
		int id_gimnasio = clienteRepository.findIdGimnasioByIdUsuario(admin.getId());
		nuevo.setId_gimnasio(id_gimnasio);
		return ResponseEntity.ok(clienteRepository.save(nuevo));

	}

	@PutMapping("/cliente")
	public ResponseEntity<?> modifyClient(@RequestBody Cliente cliente, @AuthenticationPrincipal Cliente logedClient) {
		setIdAndCreationDate(cliente, logedClient.getId());
		return ResponseEntity.ok(clienteRepository.save(cliente));

	}

	@PutMapping("/cliente/{id}")
	public Cliente modifyClientById(@RequestBody Cliente cliente, @PathVariable int id) {
		setIdAndCreationDate(cliente, id);
		return clienteRepository.save(cliente);

	}

	private void setIdAndCreationDate(Cliente cliente, int id) {
		java.time.LocalDateTime creationDate = clienteRepository.findById(id).get().getFecha_creacion();
		cliente.setId(id);
		cliente.setFecha_creacion(creationDate);
	}

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable int id) {
		Cliente delete = deleteCascade(id);
		clienteRepository.delete(delete);
		return ResponseEntity.ok().build();
	}

	private Cliente deleteCascade(int id) {
		Cliente delete = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
		timeRegistrationRepository.deleteByIdUsuario(id);
		return delete;
	}

}
