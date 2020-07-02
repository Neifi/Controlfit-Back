package es.neifi.controlfit.cliente.model;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.ForeignKey;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.neifi.controlfit.rol.model.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@NoArgsConstructor
@AllArgsConstructor
@Builder

@Entity
@Component
public class Cliente implements UserDetails{

	private static final long serialVersionUID = -5072546529605947104L;

	@Id
	@Column(name = "id",insertable = false,updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "id",insertable = false,updatable = false)
	private int id_gimnasio;
	
	private String dni;
	private String nombre;
	private String apellidos;
	private String fecha_nacimiento;

	private String email;
	private String calle;
	private String codigo_postal;
	private String ciudad;
	private String provincia;
	
	private String username;
	private String password;
	private String avatar;

	
	@CreatedDate
	private LocalDateTime fecha_creacion;
	@Builder.Default
	private LocalDateTime ultima_mod_password = LocalDateTime.now();
	private boolean verificado;
	@ElementCollection(fetch = FetchType.EAGER) // TODO eager?
	@Enumerated(EnumType.STRING)
	
	private Set<Rol> rol;
	
	
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
	
		return rol.stream().map(ur -> new SimpleGrantedAuthority("ROLE" + ur.name())).collect(Collectors.toSet());
	}
	
	@Override
	public boolean isAccountNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean isEnabled() {
		// TODO Auto-generated method stub
		return true;
	}
	

}


