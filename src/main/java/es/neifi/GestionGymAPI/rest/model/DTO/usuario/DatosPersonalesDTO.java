package es.neifi.GestionGymAPI.rest.model.DTO.usuario;

import lombok.Setter;

import lombok.Getter;

@Setter
@Getter
public class DatosPersonalesDTO {
	private String id_cliente;
	private String nombre;
	private String apellidos;
	private String fecha_nacimiento;
	private String dni;
	private String calle;
	private String ciudad;
	private String provincia;
	private int codigo_postal;
}
