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
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
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

	@ApiOperation(value = "Obtener lista completa de registros horarios")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = RegistroHorario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	@GetMapping("/horario")
	public ResponseEntity<?> obtenerTodos() {
		List<RegistroHorario> registros = registroHorarioRepo.findAll();

		if (registros.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
			return ResponseEntity.ok(registros);
		}
	}

	@ApiOperation(value = "Obtener registro horario por id")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = RegistroHorario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	@GetMapping("/horario/{id}")
	public ResponseEntity<?> obtenerPorIdUsuario(
			@ApiParam(value = "ID del cliente", required = true, type = "Integer") @PathVariable int id) {
		return ResponseEntity
				.ok(registroHorarioRepo.selectByUserId(id).orElseThrow(() -> new ClienteNotFoundException(id)));

	}

	@ApiOperation(value = "Obtener registros horarios por intervalo de fecha")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = RegistroHorario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
			
	public ResponseEntity<?> obtenerPorIntervaloDeFecha(
			@ApiParam(value = "Fecha inicio", required = true, type = "Date") @RequestParam String fechaInicio,
			@ApiParam(value = "Fecha final", required = true, type = "Date") @RequestParam String fechaFin,
			@PathVariable int id_usuario) {
		List<RegistroHorario> registros = registroHorarioRepo.selectIntervaloFecha(fechaInicio, fechaFin, id_usuario);
		if (registros.isEmpty()) {
			return ResponseEntity.notFound().build();
		} else {
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
	@ApiOperation(value = "Permite insertar un nuevo registro, tanto de entrada como de salida", notes="Si el tipo de registro es de entrada"
			+"se inserta un nuevo registro en la base de datos, si es de salida se actualiza el último registro existente de entrada")
	@ApiResponses(value = { @ApiResponse(code = 201, message = "OK", response = RegistroHorario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	@JsonFormat(shape = Shape.NUMBER_INT, pattern = "dd-MM-yyyy hh:mm:ss")
	@PostMapping(path = "/horario", params = { "clientId", "tipoRegistro" })
	public ResponseEntity<?> nuevoRegistro(
			@ApiParam(value = "Id del usuario", required = true, type = "Integer") @RequestParam int id_usuario,
			@ApiParam(value = "tipo de registro, e(entrada),s(salida)", required = true, type = "String") @RequestParam String tipoRegistro) {

		if (tipoRegistro.contentEquals("e")) {
			dtEntrada = DateTime.now().toString("HH:mm:ss");
			String fecha = DateTime.now().toString("dd-MM-yyyy");
			registroHorarioRepo.insertEntry(dtEntrada, fecha, id_usuario);
			return ResponseEntity.status(HttpStatus.CREATED).build();

		} else if (tipoRegistro.contentEquals("s")) {
			String horaSalida = DateTime.now().toString("HH:mm:ss");
			String fecha = DateTime.now().toString("dd-MM-yyyy");

			System.out.println(horaSalida);
			registroHorarioRepo.insertExit(horaSalida, fecha, id_usuario);
			return ResponseEntity.status(HttpStatus.CREATED).build();

		} else {
			return ResponseEntity.badRequest().build();
		}

	}
	
	@ApiOperation(value = "Permite calcular las horas en función de un registro horario completo")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = RegistroHorario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	@GetMapping("/calchoras")
	public ResponseEntity<String> hourDiff(@ApiParam(value = "tipo de registro, e(entrada),s(salida)", required = true,type = "RegistroHorario" ) @RequestBody RegistroHorario registroHorarioCompleto) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		String entry = registroHorarioCompleto.getHoraentrada();
		String exit = registroHorarioCompleto.getHorasalida();
		
		// Calcula las horas de diferencia en funcion de la hora de entrada y salida de un registro horario
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
