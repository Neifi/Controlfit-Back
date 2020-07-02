package es.neifi.controlfit.almacenamiento;

public class UsuarioNotFoundException extends RuntimeException {
	
	
	public UsuarioNotFoundException(int id) {
		super("No se ha encontrado al usuario con la id "+id);
	}
	public UsuarioNotFoundException() {
		super("No se ha encontrado al usuario");
	}
}
