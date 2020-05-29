package es.neifi.GestionGymAPI.rest.model.gimnasio;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter 
@Setter

@Entity
@Table(name = "gimnasio")
public class Gimnasio {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	
	//@OneToOne(mappedBy = "gyms")
	@Column(name = "id_gimnasio")
	private int id_gimnasio;
	private String nombre;
	private String ciudad;
	private String direccion;
	private int codigo_postal;
	private String provincia;
	private String pais;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,targetEntity = Cliente.class)
	private List <Cliente> cliente;
	
	
	
	
}
