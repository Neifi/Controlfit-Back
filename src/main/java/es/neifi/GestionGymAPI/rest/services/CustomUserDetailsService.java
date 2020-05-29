package es.neifi.GestionGymAPI.rest.services;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.neifi.GestionGymAPI.rest.model.usuario.Usuario;
import lombok.RequiredArgsConstructor;

@Service("UserDetailService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private final UsuarioService usuarioService;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		return usuarioService.findByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
	}

	public Usuario loadUserById(int userId) {
		// TODO Auto-generated method stub
		return usuarioService.findById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado"));
	}

}
