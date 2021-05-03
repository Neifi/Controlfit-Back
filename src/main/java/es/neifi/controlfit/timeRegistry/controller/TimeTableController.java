package es.neifi.controlfit.registrohorario.controller;


import java.text.SimpleDateFormat;


import java.util.List;

import org.joda.time.DateTime;


import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import es.neifi.controlfit.cliente.exception.ClienteNotFoundException;
import es.neifi.controlfit.registrohorario.model.RegistroHorario;
import es.neifi.controlfit.registrohorario.repo.TimeRegistrationRepository;
import es.neifi.controlfit.registrohorario.service.TimeRegistrySerice;


import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping({ "/api" })
@RequiredArgsConstructor

public class TimeTableController {

	private final TimeRegistrationRepository timeRegistrationRepository;
	private String dtEntrada = "";

	@GetMapping("/timetable")
	public ResponseEntity<?> getAll() {
		List<RegistroHorario> registros = timeRegistrationRepository.findAll();

		if (registros.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(registros);
		}
	}

	@GetMapping("/timetable/{id}")
	public ResponseEntity<?> getByClientId(@PathVariable int id) {
		return ResponseEntity
				.ok(timeRegistrationRepository.selectByUserId(id).orElseThrow(() -> new ClienteNotFoundException(id)));

	}

	public ResponseEntity<?> getByDateInterval(@RequestParam String fechaInicio, @RequestParam String fechaFin,
			@PathVariable int id_usuario) {
		List<RegistroHorario> registros = timeRegistrationRepository.selectIntervaloFecha(fechaInicio, fechaFin, id_usuario);
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
		timeRegistrationRepository.insertExit(horaSalida, fecha, id_usuario);
	}

	private void setEntryHour(int id_usuario) {
		dtEntrada = DateTime.now().toString("HH:mm:ss");
		String fecha = DateTime.now().toString("dd-MM-yyyy");
		timeRegistrationRepository.insertEntry(dtEntrada, fecha, id_usuario);
	}

	@GetMapping("/hours")
	public ResponseEntity<String> calculateHours(@RequestBody RegistroHorario registroHorarioCompleto) {

		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String entry = registroHorarioCompleto.getHoraentrada();
		String exit = registroHorarioCompleto.getHorasalida();

		return ResponseEntity.accepted().body(TimeRegistrySerice.calculateGymTimeSpent(format, entry, exit));
	}

}
