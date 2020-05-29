package es.neifi.GestionGymAPI.rest.model.gimnasio;

import org.springframework.data.jpa.repository.JpaRepository;

import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;


public interface GimnasioRepository extends JpaRepository<Cliente, Long>{

}
