package es.neifi.controlfit.test;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import es.neifi.controlfit.cliente.controller.ClienteController;
import es.neifi.controlfit.cliente.exception.ClienteNotFoundException;

public class ClientControllerTest {
	@Autowired
	private ClienteController clienteController;
	
	@Test
	public void givenUserDoesNotExistThenRecive404() throws ClienteNotFoundException, IOException{
		URL url = new URL("http://localhost:8081/");
		int id = new Integer(new Random().nextInt());
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		
		ResponseEntity<?> expected = clienteController.obtenerUno(1999);
		 assertEquals(expected, clienteController.obtenerUno(id));
	}
}
