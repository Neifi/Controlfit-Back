package es.neifi.GestionGymAPI.rest.controller;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.security.auth.message.callback.PrivateKeyCallback.Request;
import javax.servlet.ServletContext;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import es.neifi.GestionGymAPI.rest.exceptions.ApiError;
import es.neifi.GestionGymAPI.rest.exceptions.UsuarioNotFoundException;
import es.neifi.GestionGymAPI.rest.model.DTO.CrearUsuarioDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.PutClienteDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.converter.UsuarioDTOConverter;
import es.neifi.GestionGymAPI.rest.model.DTO.usuario.GetUserDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.usuario.PutUsuarioDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.usuario.SetAvatarUsuarioDTO;
import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import es.neifi.GestionGymAPI.rest.model.usuario.Usuario;
import es.neifi.GestionGymAPI.rest.model.usuario.UsuarioRepository;
import es.neifi.GestionGymAPI.rest.services.StorageService;
import es.neifi.GestionGymAPI.rest.services.UsuarioService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController

@RequiredArgsConstructor

public class UsuarioController {

	private final UsuarioService usuarioService;
	private final UsuarioDTOConverter usuarioDTOConverter;
	private final ServletContext servletContext;
	private final StorageService storageService;

	@ApiOperation(value = "Obtiene la información del usuario logeado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Usuario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })

	@GetMapping("/me")
	public Optional<Usuario> me(@AuthenticationPrincipal Usuario usuarioActual) {
		return usuarioService.findById(usuarioActual.getId_usuario());
	}

	@ApiOperation(value = "Obtiene la información del todos los usuarios")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Usuario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	@GetMapping("/usuario")
	public List getAllUsers() {
		return (List) usuarioService.findAll().stream().collect(Collectors.toList());
	}

	@ApiOperation(value = "Permite subir una imagen al servidor", notes = "Sube el avatar del usuario logeado")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Usuario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	@PutMapping(value = "usuario/avatar", consumes = MediaType.ALL_VALUE)
	public ResponseEntity<?> nuevoAvatar(@AuthenticationPrincipal Usuario usuarioActual,
			@RequestPart("file") MultipartFile file) {

		String url = null;

		if (!file.isEmpty()) {
			String imagen = storageService.store(file);
			url = MvcUriComponentsBuilder.fromMethodName(FicherosController.class, "serveFile", imagen, null).build()
					.toUriString();
			usuarioActual.setAvatar(url);
			try {
				return ResponseEntity.ok(usuarioService.edit(usuarioActual));
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No ha sido posible cargar la imagen");
			}
		} else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No ha sido posible subir la imagen");
		}

	}

	@ApiOperation(value = "Crea un usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Usuario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	@PostMapping("usuario")
	public ResponseEntity<GetUserDTO> nuevoUsuario(
			@ApiParam(value = "Datos del cliente en json", required = true, type = "JSON") @RequestBody CrearUsuarioDTO nuevoUsuario) {
		ResponseEntity<GetUserDTO> toReturn = ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioDTOConverter.convertUserToGetUserDTO(usuarioService.nuevoUsuario(nuevoUsuario)));

		return toReturn;
	}

	@ApiOperation(value = "Modifica un usuario")
	@ApiResponses(value = { @ApiResponse(code = 200, message = "OK", response = Usuario.class),
			@ApiResponse(code = 404, message = "Not Found", response = ApiError.class),
			@ApiResponse(code = 500, message = "Internal Server Error", response = ApiError.class) })
	@PutMapping("/usuario")
	public ResponseEntity<?> updatePerfil(
			@ApiParam(value = "Datos del cliente en json", required = true, type = "JSON") @RequestBody PutUsuarioDTO data,
			@ApiParam(value = "Id del cliente", required = true, type = "int") @RequestParam int id) {
			
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.putUsuario(data, id));
	}

}
