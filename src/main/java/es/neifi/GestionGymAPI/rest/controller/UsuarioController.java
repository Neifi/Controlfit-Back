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
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController

@RequiredArgsConstructor

public class UsuarioController {

	private final UsuarioService usuarioService;
	private final UsuarioDTOConverter usuarioDTOConverter;
	// private final SetAvatarUsuarioDTO setAvatarUsuarioDTO;
	private final ServletContext servletContext;
	private final StorageService storageService;

	@GetMapping("/me")
	public Optional<Usuario> me(@AuthenticationPrincipal Usuario usuarioActual) {

		// return usuarioDTOConverter.convertUserToGetUserDTO(usuarioActual);
		return usuarioService.findById(usuarioActual.getId_usuario());
	}

	@GetMapping("/usuario")
	public List getAllUsers() {

		// return usuarioDTOConverter.convertUserToGetUserDTO(usuarioActual);
		return (List) usuarioService.findAll().stream().collect(Collectors.toList());
	}

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

	@PostMapping("usuario")
	public ResponseEntity<GetUserDTO> nuevoUsuario(@RequestBody CrearUsuarioDTO nuevoUsuario) {
		ResponseEntity<GetUserDTO> toReturn = ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioDTOConverter.convertUserToGetUserDTO(usuarioService.nuevoUsuario(nuevoUsuario)));

		return toReturn;
	}

	@PutMapping("/usuario")
	public ResponseEntity<?> updatePerfil(@RequestBody PutUsuarioDTO data, @RequestParam int id) {
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.putUsuario(data, id));
	}

}
