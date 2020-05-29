package es.neifi.GestionGymAPI.rest.model.usuario;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface UsuarioRepository extends JpaRepository<Usuario,Integer>{
	
	Optional<Usuario> findByUsername(String username);
}
