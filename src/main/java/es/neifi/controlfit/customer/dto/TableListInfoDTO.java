package es.neifi.controlfit.customer.dto;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class TableListInfoDTO {
	private String nombre;
	private String apellidos;
	private String dni;
	private String email;
	private String fecha_inscripcion;
}
