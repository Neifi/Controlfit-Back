package es.neifi.controlfit.security.jwt.controller;


import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.neifi.controlfit.cliente.dto.ClienteDtoConverter;
import es.neifi.controlfit.cliente.model.Cliente;
import es.neifi.controlfit.security.jwt.model.LoginRequest;
import es.neifi.controlfit.security.jwt.providers.JwtProvider;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtProvider provider;
	private final ClienteDtoConverter converter;
	
	@PostMapping("auth/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
		
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Cliente cliente =(Cliente) authentication.getPrincipal();
		
		String token = provider.generateToken(authentication);
		return ResponseEntity.status(HttpStatus.CREATED).body(converter.convertUserAndTokenToJwtUserResponse(cliente,token ));
	}


}
