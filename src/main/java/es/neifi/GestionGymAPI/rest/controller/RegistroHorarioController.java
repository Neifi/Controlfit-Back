package es.neifi.GestionGymAPI.rest.controller;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.chrono.ChronoLocalDate;
import java.time.chrono.Chronology;
import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.stream.Collectors;

import org.joda.time.DateTime;
import org.joda.time.Days;
import org.joda.time.Hours;
import org.joda.time.Minutes;
import org.joda.time.Seconds;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;
import com.fasterxml.jackson.databind.JsonMappingException;

import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import es.neifi.GestionGymAPI.rest.model.DTO.InfoClienteDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.CrearClienteDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.converter.ClientDetailsDTOConverter;
import es.neifi.GestionGymAPI.rest.exceptions.ApiError;
import es.neifi.GestionGymAPI.rest.exceptions.ClienteNotFoundException;
import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorario;
import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorarioRepository;
import es.neifi.GestionGymAPI.rest.model.cliente.ClienteRepository;

import es.neifi.GestionGymAPI.rest.exceptions.ApiError;
import es.neifi.GestionGymAPI.rest.exceptions.ClienteNotFoundException;
import es.neifi.GestionGymAPI.rest.model.DTO.CrearClienteDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.InfoClienteDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.converter.ClientDetailsDTOConverter;
import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import es.neifi.GestionGymAPI.rest.model.cliente.ClienteRepository;
import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorario;
import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorarioRepository;

import lombok.RequiredArgsConstructor;

@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
@RestController
@RequestMapping({ "/api" })
@RequiredArgsConstructor
public class RegistroHorarioController {

	private final RegistroHorarioRepository registroHorarioRepo;
	private final ClienteRepository clienteRepository;
	private final ClientDetailsDTOConverter clienteDetailsDTOConverter;
	private String dtEntrada = "";


	@GetMapping("/horario")
	public ResponseEntity<?> obtenerTodos() {
		List<RegistroHorario> registros = registroHorarioRepo.findAll();

		if (registros.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(registros);
		}
	}
	
	
	@GetMapping("/horario/{id}")
	public ResponseEntity<?> obtenerPorIdUsuario(@PathVariable int id) {
		return ResponseEntity.ok(registroHorarioRepo.selectByUserId(id)
				.orElseThrow(() -> new ClienteNotFoundException(id)));
		
	}
	
	
	public ResponseEntity<?> obtenerPorIntervaloDeFecha(@RequestParam String fechaInicio, @RequestParam String fechaFin,
			@PathVariable int id_usuario) {
		List <RegistroHorario> registros = registroHorarioRepo.selectIntervaloFecha(fechaInicio, fechaFin, id_usuario);
		if(registros.isEmpty()) {
			return ResponseEntity.notFound().build();
		}else {
			return ResponseEntity.ok(registros);
		}
	}

	/**
	 * 
	 * @param nuevo        Entidad registroHorario
	 * @param clientId     id del cliente al cual pertenece el registro
	 * @param tipoRegistro tipo de registro, entrada o salida
	 * @return
	 */
//	LocalTime time = LocalTime.now();

	@JsonFormat(shape = Shape.NUMBER_INT, pattern = "dd-MM-yyyy hh:mm:ss")
	@PostMapping(path = "/horario", params = { "clientId", "tipoRegistro" })
	public ResponseEntity<?> nuevoRegistro(@RequestParam int id_usuario, @RequestParam String tipoRegistro) {

		if (tipoRegistro.contentEquals("e")) {
			dtEntrada = DateTime.now().toString("HH:mm:ss");
			String fecha = DateTime.now().toString("dd-MM-yyyy");
			registroHorarioRepo.insertEntry(dtEntrada,fecha,id_usuario);
			return ResponseEntity.status(HttpStatus.CREATED).build();

		} else if (tipoRegistro.contentEquals("s")) {
			String horaSalida = DateTime.now().toString("HH:mm:ss");
			String fecha = DateTime.now().toString("dd-MM-yyyy");

			System.out.println(horaSalida);
			registroHorarioRepo.insertExit(horaSalida,fecha,id_usuario);
			return ResponseEntity.status(HttpStatus.CREATED).build();

		} else {
			return ResponseEntity.badRequest().build();
		}

	}
	
	
	@GetMapping("/calchoras")
	public ResponseEntity<String> hourDiff(@RequestBody RegistroHorario registroHorarioCompleto) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String entry = registroHorarioCompleto.getHoraentrada();
		String exit = registroHorarioCompleto.getHorasalida();
		
		try {
			Date start = format.parse(entry);
			Date end = format.parse(exit);

			DateTime startTime = new DateTime(start);
			DateTime exitTime = new DateTime(end);
			int hours = Hours.hoursBetween(startTime, exitTime).getHours() % 24;
			int minutes = Minutes.minutesBetween(startTime, exitTime).getMinutes() % 60;
			int seconds = Seconds.secondsBetween(startTime, exitTime).getSeconds() % 60;

			String difference = String.valueOf(hours + ":" + minutes + ":" + seconds);

			return ResponseEntity.accepted().body(difference);
		} catch (ParseException e) {
			
			e.printStackTrace();
		}
		return ResponseEntity.badRequest().body(" ");
	}

}
