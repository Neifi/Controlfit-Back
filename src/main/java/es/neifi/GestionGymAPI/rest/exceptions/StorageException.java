package es.neifi.GestionGymAPI.rest.exceptions;

public class StorageException extends RuntimeException {

	/**
	 * Excepcion de almacenamiento
	 */
	private static final long serialVersionUID = -5502351264978098291L;

	public StorageException(String message) {
		super(message);
	}

	public StorageException(String message, Throwable cause) {
		super(message, cause);
	}

}