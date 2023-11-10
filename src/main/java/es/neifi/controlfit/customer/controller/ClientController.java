package es.neifi.controlfit.customer.controller;

import es.neifi.controlfit.customer.storage.StorageService;
import es.neifi.controlfit.customer.dto.ClientDto;
import es.neifi.controlfit.customer.dto.ClientDtoConverter;
import es.neifi.controlfit.customer.dto.TableListInfoDTO;
import es.neifi.controlfit.customer.exception.ClienteNotFoundException;
import es.neifi.controlfit.customer.model.Customer;
import es.neifi.controlfit.customer.repo.ClienteRepository;
import es.neifi.controlfit.customer.service.ClientService;
import es.neifi.controlfit.customer.ficheros.controller.FileController;
import es.neifi.controlfit.customer.timeRegistry.service.TimeRegistryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping({ "/api" })

@RequiredArgsConstructor
public class ClientController {

	private final ClienteRepository clienteRepository;

	private final TimeRegistryService timeRegistryService;
	private final ClientService clientService;
	private final StorageService storageService;
	private final ClientDtoConverter clientDtoConverter;


	@PutMapping(value = "user/avatar", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> uploadClientAvatar(ClientDto clientDto,
												@RequestPart("file") MultipartFile file) {
		String url = "";
		if (!file.isEmpty()) {
			url = storeInLocal(file);
			clientDto.setAvatar(url);
			
			try {
				Optional<Customer> clientEntity = findClientByUsername(clientDto);

				return ResponseEntity.ok(clientService.updateClientById(clientDto, clientEntity.get().getId()));
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Error uploading the image");
		}

	}

	public Optional<Customer> findClientByUsername(ClientDto currentClient) {
		Optional<Customer> id = clientService.searchClientByUsername(currentClient.getUsername());
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
	public ResponseEntity<?> getAllClients() {
		List<Customer> customers = clientService.findAllClientsOrderedById();
		if (customers.isEmpty()) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No users found");
		} else {
			List<TableListInfoDTO> dtoList = convertDtoToList(customers);
			return ResponseEntity.ok(dtoList);
		}

	}

	private List<TableListInfoDTO> convertDtoToList(List<Customer> customers) {
		List<TableListInfoDTO> dtoList = customers.stream().map(clientDtoConverter::convertToDto)
				.collect(Collectors.toList());
		return dtoList;
	}

	@GetMapping("/cliente/{id}")
	public ResponseEntity<?> getOneClient(@PathVariable int id) {
		try {

			return ResponseEntity.ok(clienteRepository.findById(id));
		} catch (Exception ex) {
			throw new ResponseStatusException(HttpStatus.NOT_FOUND, ex.getMessage());
		}
	}

	@PostMapping("/cliente")
	public ResponseEntity<Customer> createClient(@RequestBody Customer nuevo) {
		return ResponseEntity.ok(clienteRepository.save(nuevo));

	}

	@PutMapping("/cliente")
	public ResponseEntity<?> updateClient(@RequestBody Customer customer) {
		return ResponseEntity.ok(clienteRepository.save(customer));

	}

	@PutMapping("/cliente/{id}")
	public Customer updateClientById(@RequestBody Customer customer, @PathVariable int id) {
		setIdAndCreationDate(customer, id);
		return clienteRepository.save(customer);

	}

	private void setIdAndCreationDate(Customer customer, int id) {
		java.time.LocalDateTime creationDate = clienteRepository.findById(id).get().getCreatedAt();
		customer.setId(id);
		customer.setCreatedAt(creationDate);
	}

	@DeleteMapping("/cliente/{id}")
	public ResponseEntity<?> deleteClient(@PathVariable int id) {
		Customer delete = deleteCascade(id);
		clienteRepository.delete(delete);
		return ResponseEntity.ok().build();
	}

	private Customer deleteCascade(int id) {
		Customer delete = clienteRepository.findById(id).orElseThrow(() -> new ClienteNotFoundException(id));
		timeRegistryService.deleteById(id);
		return delete;
	}

}
