package es.neifi.GestionGymAPI.rest.services;

import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.server.ResponseStatusException;

import es.neifi.GestionGymAPI.rest.exceptions.UsuarioNotFoundException;
import es.neifi.GestionGymAPI.rest.model.DTO.CrearUsuarioDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.PutClienteDTO;
import es.neifi.GestionGymAPI.rest.model.DTO.converter.UsuarioDTOConverter;
import es.neifi.GestionGymAPI.rest.model.DTO.usuario.PutUsuarioDTO;
import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import es.neifi.GestionGymAPI.rest.model.cliente.ClienteRepository;
import es.neifi.GestionGymAPI.rest.model.rol.Rol;
import es.neifi.GestionGymAPI.rest.model.usuario.Usuario;
import es.neifi.GestionGymAPI.rest.model.usuario.UsuarioRepository;
import es.neifi.GestionGymAPI.rest.services.baseServices.BaseService;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UsuarioService extends BaseService<Usuario, Integer,UsuarioRepository>{
	
	private final PasswordEncoder passwordEncoder;
	private final ClienteRepository clienteRepository;
	private UsuarioDTOConverter usuarioDTOConverter;
	public Optional<Usuario> findByUsername(String username){
		return this.repositorio.findByUsername(username);
	}
	
	public Usuario nuevoUsuario (CrearUsuarioDTO usuario) {
		
		
			Usuario newUsuario = Usuario.builder()
					.username(usuario.getUsername())
					.password(passwordEncoder.encode(usuario.getPassword()))
					.avatar(null)
					.rol(Stream.of(Rol.UNVERIFIED).collect(Collectors.toSet()))
					.build();
			try {
				Usuario toReturn = save(newUsuario);
				return toReturn;
			} catch (DataIntegrityViolationException e) {
				throw new ResponseStatusException(HttpStatus.BAD_REQUEST,"Nombre de usuario existente");
			}
		
	}
	
	
	public Usuario putUsuario(@RequestParam PutUsuarioDTO usuarioDTO, @RequestParam int id) {
		return findById(id).map(u ->{
			u = usuarioDTOConverter.convertToUsuario(usuarioDTO);
			return save(u);
		}).orElseThrow(() -> new UsuarioNotFoundException(id));
		
	}
}
