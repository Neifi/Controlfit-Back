package es.neifi.controlfit.security.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import es.neifi.controlfit.cliente.service.CustomUserDetailsService;
import es.neifi.controlfit.security.jwt.entrypoints.JwtAuthenticationEntryPoint;
import es.neifi.controlfit.security.jwt.filtros.JwtAuthorizationFilter;
import lombok.RequiredArgsConstructor;

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
	private static final String[] AUTH_WHITELIST = { "/swagger-resources/**", "/swagger-ui.html", "/v2/api-docs",
			"/webjars/**" };

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
		http.csrf().disable().exceptionHandling().authenticationEntryPoint(authenticationEntryPoint).and()
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS).and().authorizeRequests()
				.antMatchers(HttpMethod.POST, "/auth/login").permitAll().antMatchers(HttpMethod.GET, "/swagger-ui.html")
				.permitAll().antMatchers(HttpMethod.GET, "/webjars/**").permitAll()
				.antMatchers(HttpMethod.GET, "/client/**", "/timetable/**", "/horario/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.POST, "/client/**", "/timetable/**", "/horario/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.PUT, "/client/**", "/timetable/**", "/horario/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.DELETE, "/client/**", "/timetable/**", "/horario/**").hasRole("ADMIN")
				.antMatchers(HttpMethod.GET, "/user/**").hasAnyRole("ADMIN", "USER").anyRequest().authenticated();
		http.addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);

	}
	
	@Override
	public void configure(WebSecurity web) throws Exception {

		web.ignoring().antMatchers(AUTH_WHITELIST);
	}

}