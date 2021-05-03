package es.neifi.controlfit.cliente.service;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.neifi.controlfit.cliente.model.Cliente;
import lombok.RequiredArgsConstructor;

@Service("UserDetailService")
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService{
	
	private final ClientService clientService;
	
	@Override
	public UserDetails loadUserByUsername(String username) {
		
		return clientService.searchClientByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Cliente no encontrado"));
	}

	public Cliente loadUserById(int userId) {
		// TODO Auto-generated method stub
		return clientService.findClientById(userId)
				.orElseThrow(() -> new UsernameNotFoundException("Cliente no encontrado"));
	}

}
