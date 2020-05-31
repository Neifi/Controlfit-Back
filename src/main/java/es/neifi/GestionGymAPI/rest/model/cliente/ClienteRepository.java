package es.neifi.GestionGymAPI.rest.model.cliente;

import java.util.List;
import java.util.stream.Stream;

import javax.persistence.NamedQuery;

import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import es.neifi.GestionGymAPI.rest.model.usuario.Usuario;

public interface ClienteRepository extends JpaRepository<Cliente, Integer>{

	@Modifying
	@Query("UPDATE Cliente c SET c.email = :email WHERE c.id = :id")
	int updateDatosContacto(@Param("email") String email, @Param("id") int id);
	
	@Modifying
	@Query( value = "UPDATE Cliente c SET c.nombre = ?, c.apellidos = ?, c.fecha_nacimiento = ?, c.dni = ?, c.calle = ?, c.ciudad= ?, c.provincia = ?, c.codigo_postal = ? WHERE c.id = ?", nativeQuery = true)
	int updateDatosPersonales(@Param("nombre") String nombre,@Param("apellidos") String apellidos,@Param("fechana") String fecha_nacimiento,@Param("dni") String dni,@Param("calle") String calle,@Param("ciudad") String ciudad,@Param("provincia") String provincia,@Param("codigo_postal") String codigo_postal, @Param("id") int id);

	List<Cliente> findAllByOrderByIdAsc();

	
	@Query(value = "SELECT id_gimnasio FROM Cliente INNER JOIN usuario ON cliente.id = usuario.id_usuario WHERE id_usuario =:id_usuario",nativeQuery = true)
	Cliente findIdGimnasioByIdUsuario(@Param("id_usuario")int id_usuario);
	

	

	
	
}
