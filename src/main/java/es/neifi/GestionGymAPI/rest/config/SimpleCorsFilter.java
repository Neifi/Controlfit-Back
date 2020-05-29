package es.neifi.GestionGymAPI.rest.config;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

@Component
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SimpleCorsFilter implements Filter{

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		final HttpServletResponse httpResponse = (HttpServletResponse)response;
		final HttpServletRequest httpRequest = (HttpServletRequest)request;
		httpResponse.setHeader("Access-Control-Allow-Origin", "*");//cambiar en producccion
		httpResponse.setHeader("Access-Control-Allow-Methods", "POST,PUT,GET,OPTIONS,DELETE");
		httpResponse.setHeader("Access-Control-Allow-Headers", "Authorization,Content-Type");
		httpResponse.setHeader("Access-Control-Max-Age", "3600");
		
		if(HttpMethod.OPTIONS.name().equalsIgnoreCase(httpRequest.getMethod())) {
			httpResponse.setStatus(HttpStatus.OK.value());
		}else{
			chain.doFilter(request, response);
		};
	}
	
}
