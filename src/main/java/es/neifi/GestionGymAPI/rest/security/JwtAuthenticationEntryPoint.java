package es.neifi.GestionGymAPI.rest.security;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.databind.ObjectMapper;

import es.neifi.GestionGymAPI.rest.exceptions.ApiError;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor

/**
 * En caso de que la autenticación sea invalida,o el token haya expirado se lanza una respuesta,
 * al cliente indicando que no esta autorizado para obtener el recurso.
 * @author neifi
 *
 */
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint{
	
	private final ObjectMapper mapper;
	
	@Override
	public void commence(HttpServletRequest request, HttpServletResponse response,
			AuthenticationException authException) throws IOException, ServletException {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
			response.setContentType("application/json");
			
			ApiError apiError = new ApiError(HttpStatus.UNAUTHORIZED, authException.getMessage());
			String strApiError = mapper.writeValueAsString(apiError);
			PrintWriter writer = response.getWriter();
			writer.println(strApiError);
			
	}
	
}
