package es.neifi.GestionGymAPI.rest.controller;

import java.util.stream.Collectors;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import es.neifi.GestionGymAPI.rest.model.usuario.Usuario;
import es.neifi.GestionGymAPI.rest.model.DTO.converter.UsuarioDTOConverter;
import es.neifi.GestionGymAPI.rest.model.DTO.usuario.GetUserDTO;
import es.neifi.GestionGymAPI.rest.security.jwt.JwtProvider;
import es.neifi.GestionGymAPI.rest.security.jwt.model.JwtUserResponse;
import es.neifi.GestionGymAPI.rest.security.jwt.model.LoginRequest;
import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class AuthenticationController {
	
	private final AuthenticationManager authenticationManager;
	private final JwtProvider provider;
	private final UsuarioDTOConverter converter;
	
	@PostMapping("auth/login")
	public ResponseEntity<?> login(@Valid @RequestBody LoginRequest loginRequest){
		Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));
		
		SecurityContextHolder.getContext().setAuthentication(authentication);
		Usuario usuario = (Usuario) authentication.getPrincipal();
		
		String token = provider.generateToken(authentication);
		return ResponseEntity.status(HttpStatus.CREATED).body(converter.convertUserAndTokenToJwtUserResponse(usuario,token ));
	}
	
	public GetUserDTO me(@AuthenticationPrincipal Usuario usuario) {
		return converter.convertUserToGetUserDTO(usuario);
	}


}
