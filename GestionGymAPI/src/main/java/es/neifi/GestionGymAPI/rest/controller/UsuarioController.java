package es.neifi.GestionGymAPI.rest.controller;

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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import es.neifi.GestionGymAPI.rest.DTO.CrearUsuarioDTO;
import es.neifi.GestionGymAPI.rest.DTO.GetUserDTO;
import es.neifi.GestionGymAPI.rest.DTO.SetAvatarUsuarioDTO;
import es.neifi.GestionGymAPI.rest.DTO.converter.UsuarioDTOConverter;
import es.neifi.GestionGymAPI.rest.model.Usuario;
import es.neifi.GestionGymAPI.rest.model.UsuarioRepository;
import es.neifi.GestionGymAPI.services.StorageService;
import es.neifi.GestionGymAPI.services.UsuarioService;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

@RestController

@RequiredArgsConstructor

public class UsuarioController{

	private final UsuarioService usuarioService;
	private final UsuarioDTOConverter usuarioDTOConverter;
	//private final SetAvatarUsuarioDTO setAvatarUsuarioDTO;
	private final ServletContext servletContext;
	private final StorageService storageService;

	@GetMapping("/me")
	public GetUserDTO me(@AuthenticationPrincipal Usuario usuarioActual) {

		return usuarioDTOConverter.convertUserToGetUserDTO(usuarioActual);
	}

	@PutMapping(value = "user/upload", consumes = MediaType.ALL_VALUE) 
	public ResponseEntity<?> nuevoAvatar(@AuthenticationPrincipal Usuario usuarioActual,
		 @RequestPart("file") MultipartFile file) {

		String url = null;

		
		if (!file.isEmpty()) {
			String imagen = storageService.store(file);
			url = MvcUriComponentsBuilder.fromMethodName(FicherosController.class, "serveFile", imagen, null).build().toUriString();;
			//setAvatarUsuarioDTO.setUrl(url);
			usuarioActual.setAvatar(url);	
			try {
				
				return ResponseEntity.ok(usuarioService.edit(usuarioActual));
			} catch (Exception e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No ha sido posible cargar la imagen");
			}
		}else {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"No ha sido posible subir la imagen");
		}
	
		
	}

	//@PostMapping("/registro")
	public ResponseEntity<GetUserDTO> nuevoUsuario(@RequestBody CrearUsuarioDTO nuevoUsuario) {
		ResponseEntity<GetUserDTO> toReturn = ResponseEntity.status(HttpStatus.CREATED)
				.body(usuarioDTOConverter.convertUserToGetUserDTO(usuarioService.nuevoUsuario(nuevoUsuario)));
		
		// Token y mail
		//String appUrl = servletContext.getContextPath();

		return toReturn;
	}
}