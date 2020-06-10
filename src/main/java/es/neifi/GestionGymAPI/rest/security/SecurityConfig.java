package es.neifi.GestionGymAPI.rest.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;


import es.neifi.GestionGymAPI.rest.security.jwt.JwtAuthorizationFilter;
import es.neifi.GestionGymAPI.rest.services.CustomUserDetailsService;
import io.jsonwebtoken.lang.Arrays;
import es.neifi.GestionGymAPI.rest.services.CustomUserDetailsService;

import lombok.RequiredArgsConstructor;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@RequiredArgsConstructor
@Order(1)

public class SecurityConfig extends WebSecurityConfigurerAdapter {

	private final PasswordEncoder passwordEncoder;
	private final CustomUserDetailsService userDetailsService;
	private final JwtAuthenticationEntryPoint authenticationEntryPoint;
	private final JwtAuthorizationFilter filter;
	private static final String[] AUTH_WHITELIST = {
	        "/swagger-resources/**",
	        "/swagger-ui.html",
	        "/v2/api-docs",
	        "/webjars/**"
	};
	@Override

	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder);
	}

	@Bean()
	@Override
	public AuthenticationManager authenticationManagerBean() throws Exception {
		return super.authenticationManagerBean();
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		// control de acceso
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
				.sessionManagement()
				.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
					.authorizeRequests()
					.antMatchers(HttpMethod.POST, "/auth/login").permitAll()
					.antMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
					.antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
					.antMatchers(HttpMethod.GET, "/cliente/**", "/registro/**", "/horario/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.POST, "/cliente/**", "/registro/**", "/horario/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.PUT, "/cliente/**", "/registro/**", "/horario/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.DELETE, "/cliente/**", "/registro/**", "/horario/**").hasRole("ADMIN")
					.antMatchers(HttpMethod.POST, "user/upload").hasAnyRole("ADMIN","USER")
					.antMatchers(HttpMethod.GET, "/user/**").hasAnyRole("ADMIN","USER")
					.anyRequest().authenticated();
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
		
	;
		
	}

	@Override
	public void configure(WebSecurity web) throws Exception {
		
		web.ignoring().antMatchers(AUTH_WHITELIST);
	}
	
	

}