package es.neifi.controlfit.gimnasio.model;

import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.neifi.controlfit.customer.model.Customer;
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
	
	private int id;
	private String nombre;
	private String ciudad;
	private String direccion;
	private int codigo_postal;
	private String provincia;
	private String pais;
	
	@JsonIgnore
	@OneToMany(fetch = FetchType.EAGER,targetEntity = Customer.class)
	private List <Customer> customer;
	
	
	
	
}
