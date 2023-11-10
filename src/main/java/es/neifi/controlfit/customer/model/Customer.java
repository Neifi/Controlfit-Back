package es.neifi.controlfit.customer.model;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.stereotype.Component;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Component
@Table(name = "customer")
public class Customer {

	private static final long serialVersionUID = -5072546529605947104L;

	@Id
	@Column(name = "id")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name = "gym_id")
	private int gymId;
	
	private String dni;
	@Column(name = "first_name")
	private String firstName;
	@Column(name = "last_name")
	private String lastName;
	@Column(name = "birth_date")
	private String birthDate;

	private String street;
	@Column(name = "postal_code")
	private String postalCode;
	private String city;
	private String province;

	private String email;
	private String username;
	private String password;
	private String avatar;


	@CreatedDate
	@Column(name = "created_at")
	private LocalDateTime createdAt;
	@Builder.Default
	@Column(name = "updated_at")
	private LocalDateTime updatedAt = LocalDateTime.now();
	private boolean verified;


}


