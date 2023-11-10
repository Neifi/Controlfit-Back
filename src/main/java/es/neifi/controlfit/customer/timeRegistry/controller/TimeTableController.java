package es.neifi.controlfit.customer.timeRegistry.controller;


import java.text.SimpleDateFormat;


import java.util.List;

import es.neifi.controlfit.customer.timeRegistry.service.TimeRegistryService;
import org.joda.time.DateTime;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.neifi.controlfit.customer.exception.ClienteNotFoundException;
import es.neifi.controlfit.customer.timeRegistry.model.TimeRegistry;
import es.neifi.controlfit.customer.timeRegistry.repo.TimeRegistryRepository;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({ "/api" })
@RequiredArgsConstructor

public class TimeTableController {

	private final TimeRegistryRepository timeRegistryRepository;
	private String dtEntrada = "";

	@GetMapping("/timetable")
	public ResponseEntity<?> getAll() {
		List<TimeRegistry> registros = timeRegistryRepository.findAll();

		if (registros.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(registros);
		}
	}

	@GetMapping("/timetable/{id}")
	public ResponseEntity<?> getByClientId(@PathVariable int id) {
		return ResponseEntity
				.ok(timeRegistryRepository.selectByUserId(id).orElseThrow(() -> new ClienteNotFoundException(id)));

	}

	public ResponseEntity<?> getByDateInterval(@RequestParam String fechaInicio, @RequestParam String fechaFin,
			@PathVariable int id_usuario) {
		List<TimeRegistry> registros = timeRegistryRepository.selectIntervaloFecha(fechaInicio, fechaFin, id_usuario);
		if (registros.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(registros);
		}
	}

	@PostMapping(path = "/timetable", params = { "clientId", "tipoRegistro" })
	public ResponseEntity<?> newTimeTableField(@RequestParam int clientId, @RequestParam String registryType) {

		if (registryType.contentEquals("e")) {
			setEntryHour(clientId);
			return ResponseEntity.status(HttpStatus.CREATED).build();
		}
		
		if (registryType.contentEquals("s")) {
			setExitHour(clientId);
			return ResponseEntity.status(HttpStatus.CREATED).build();

		}

		return ResponseEntity.badRequest().build();

	}

	private void setExitHour(int id_usuario) {
		String horaSalida = DateTime.now().toString("HH:mm:ss");
		String fecha = DateTime.now().toString("dd-MM-yyyy");

		System.out.println(horaSalida);
		timeRegistryRepository.insertExit(horaSalida, fecha, id_usuario);
	}

	private void setEntryHour(int id_usuario) {
		dtEntrada = DateTime.now().toString("HH:mm:ss");
		String fecha = DateTime.now().toString("dd-MM-yyyy");
		timeRegistryRepository.insertEntry(dtEntrada, fecha, id_usuario);
	}

	@GetMapping("/hours")
	public ResponseEntity<String> calculateHours(@RequestBody TimeRegistry timeRegistryCompleto) {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String entry = timeRegistryCompleto.getHoraentrada();
		String exit = timeRegistryCompleto.getHorasalida();

		return ResponseEntity.accepted().body(TimeRegistryService.calculateGymTimeSpent(format, entry, exit));
	}

}
