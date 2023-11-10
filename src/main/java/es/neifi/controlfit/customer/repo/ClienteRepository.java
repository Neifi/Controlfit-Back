package es.neifi.controlfit.customer.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.neifi.controlfit.customer.model.Customer;

public interface ClienteRepository extends JpaRepository<Customer, Integer>{

	
	List<Customer> findAllByOrderByIdAsc();

	
	@Query(value = "SELECT id  AS id FROM Cliente INNER JOIN "
			+ "usuario ON cliente.id = usuario.id WHERE id =:id_usuario",nativeQuery = true)
	int findIdGimnasioByIdUsuario(@Param("id_usuario")int id_usuario);

	Optional<Customer> findCustomerByUsername(String username);
	

	
}
