package es.neifi.controlfit.security.jwt.filtros;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;


import es.neifi.controlfit.cliente.model.Cliente;
import es.neifi.controlfit.cliente.service.ClientService;
import es.neifi.controlfit.security.jwt.providers.JwtProvider;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;

@Log
@Component
@RequiredArgsConstructor

/**
 * Verifica si el token es válido
 * y obtiene el id del usuario a partir del contenido del token.
 * @author neifi
 *
 */
public class JwtAuthorizationFilter extends OncePerRequestFilter{
	
	private final JwtProvider provider;
	private final ClientService clientService;
	
	
	@Override
	/**
	 * Comprueba la validez del token, si es valido almacena al usuario en el contexto de seguridad
	 */
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		try {
			String token = getJwtFromRequest(request);
			if(StringUtils.hasText(token) && provider.validateToken(token)) {
				int userId = provider.getUserIdFromJWT(token);
				Cliente user =  clientService.findClientById(userId).get();

				UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(user, user.getRol(),user.getAuthorities());
				authentication.setDetails(new WebAuthenticationDetails(request));
				SecurityContextHolder.getContext().setAuthentication(authentication);
			}
		} catch (Exception e) {
			log.info("No se ha podido establecer la autenticación de usuario en el contexto de seguridad");
		}
		
		filterChain.doFilter(request, response);
	}

	/**
	 * Obtiene el token a partir de la petición en caso de no obtener el token
	 * devuelve nulo
	 * @param request
	 * @return
	 */
	private String getJwtFromRequest(HttpServletRequest request) {
		String bearerToken = request.getHeader(JwtProvider.TOKEN_HEADER);
		if(StringUtils.hasText(bearerToken) && bearerToken.startsWith(JwtProvider.TOKEN_PREFIX)) {
			return bearerToken.substring(JwtProvider.TOKEN_PREFIX.length(),bearerToken.length());
		}else {
			return null;
		}
	}
	
}
