package es.neifi.GestionGymAPI.rest.model.gimnasio;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;


public interface GimnasioRepository extends JpaRepository<Cliente, Long>{
	
	@Query(value = "SELECT id_gimnasio from gimnasio where id_cliente = ?", nativeQuery = true)
	public Gimnasio findIdGimnasioByIdCliente(int id_cliente);
}
