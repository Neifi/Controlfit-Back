package es.neifi.controlfit.gimnasio.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import es.neifi.controlfit.cliente.model.Cliente;
import es.neifi.controlfit.gimnasio.model.Gimnasio;


public interface GimnasioRepository extends JpaRepository<Cliente, Long>{
	
	@Query(value = "SELECT id_gimnasio from gimnasio where id_cliente = ?", nativeQuery = true)
	public Gimnasio findIdGimnasioByIdCliente(int id_cliente);
}
