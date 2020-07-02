package es.neifi.controlfit.security.jwt.model;

import javax.validation.constraints.NotBlank;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor

/**
 * Modelo de petición de login
 * @author neifi
 *
 */
public class LoginRequest {
	@NotBlank
	private String username;
	@NotBlank
	private String password;
}
