package es.neifi.controlfit.cliente.repo;

import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

import javax.persistence.NamedQuery;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.neifi.controlfit.cliente.model.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	
	List<Cliente> findAllByOrderByIdAsc();

	
	@Query(value = "SELECT id  AS id FROM Cliente INNER JOIN "
			+ "usuario ON cliente.id = usuario.id WHERE id =:id_usuario",nativeQuery = true)
	int findIdGimnasioByIdUsuario(@Param("id_usuario")int id_usuario);

	@Query(value="select * from cliente where username =:username",nativeQuery = true )
	Optional<Cliente> findByUsername(@Param("username")String username);
	

	
}
