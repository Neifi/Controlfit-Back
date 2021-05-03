package es.neifi.controlfit.test;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import es.neifi.controlfit.cliente.service.ClienteService;

class ClienteServiceTest {
	
	@Autowired
	private ClienteService clienteService;
	@Test
	void findAllClientsThatMatchWithSomeCriteriaThenReturnClientsInList() {
		
		
		
		List<String> criteria = new ArrayList<String>();
		criteria.add("Admin");
		criteria.add("User");
		criteria.add("casa");
		criteria.add("casa");
		
		
		
		//clienteService.findAll().stream().forEach(System.out::println);
	}

}
