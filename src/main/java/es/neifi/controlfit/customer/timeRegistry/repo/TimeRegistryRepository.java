package es.neifi.controlfit.customer.timeRegistry.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import es.neifi.controlfit.customer.timeRegistry.model.TimeRegistry;


public interface TimeRegistryRepository extends JpaRepository<TimeRegistry, Integer>{
	
	@Transactional
	@Modifying
	@Query(value="insert into registrohorario (horaentrada,id_cliente,fecha) values (?,?,?) RETURNING id_registrohorario", nativeQuery = true)
	public int insertEntry(String horaEntrada, String fecha, int id_usuario);
	
	@Transactional
	@Modifying
	@Query(value="insert into registrohorario (horasalida,id_cliente,fecha) values (?,?,?) CONFLICT id_cliente UPDATE"
			+ "SET ", nativeQuery = true)
	public int insertExit(String horaSalida,String fecha,int id_usuario);
	

	@Query(value = "SELECT * FROM registrohorario WHERE fecha >= ?, AND fecha > ? AND id_usuario = ? ", nativeQuery = true)
	public List<TimeRegistry> selectIntervaloFecha(String fechaInicio, String fechaFin, int id_usuario);
	
	@Query(value = "SELECT * FROM registroHorario WHERE id_usuario = ?", nativeQuery = true)
	public  Optional<List<TimeRegistry>> selectByUserId(int id);
	
	@Transactional
	@Modifying
	@Query(value="DELETE FROM registrohorario WHERE id_usuario = ?", nativeQuery = true)
	public int deleteById(int id_usuario);
	
}
