package es.neifi.GestionGymAPI.rest.model.usuario;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;

import com.fasterxml.jackson.annotation.JsonIgnore;

import es.neifi.GestionGymAPI.rest.model.cliente.Cliente;
import es.neifi.GestionGymAPI.rest.model.registrohorario.RegistroHorario;
import es.neifi.GestionGymAPI.rest.model.rol.Rol;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@Data
@Entity
@Builder
@Setter
@EntityListeners(AuditingEntityListener.class)
@Table(name = "usuario", schema = "public")
public class Usuario implements UserDetails {

	static final long serialVersionUID = 7615817677864376347L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id_usuario;

	private String username;
	private String password;
	private String avatar;

	
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name="id_usuario", referencedColumnName = "id")
	private Cliente cliente;
	
	
	@CreatedDate
	private LocalDateTime fecha_creacion;
	@Builder.Default
	private LocalDateTime ultima_mod_password = LocalDateTime.now();
	private boolean verificado;
	@ElementCollection(fetch = FetchType.EAGER) // TODO eager?
	@Enumerated(EnumType.STRING)
	@Column(name = "rol")
	private Set<Rol> rol;
	

	
	public Usuario() {
		super();
		this.verificado = false;
	}
	
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
	
	
	//Token y mail
	
	
}
