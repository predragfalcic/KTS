package com.projekat.kts.model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

/**
 * 
 * @author Predrag Falcic
 *
 */
@Entity
public class AppUser implements UserDetails {

	private static final long serialVersionUID = 1L;
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;
	private String name;
	
	@Column(unique = true)
	private String username;
	
	private String password;
	
	@ElementCollection(fetch = FetchType.EAGER)
	private List<String> roles = new ArrayList<>();

	@ManyToOne
	@JoinColumn(name = "apartmen_id")
	@JsonIgnoreProperties(value = {"apartmen_tenats"}, allowSetters=true)
	private Apartmen apartmen; // apartmen in which he lives
	
	@ManyToOne
	@JoinColumn(name = "institution_id")
	@JsonIgnoreProperties(value = {"workers"}, allowSetters=true)
	private Institution institution; // institution in which he works
	
	@OneToMany(mappedBy = "worker", fetch = FetchType.EAGER)
	@JsonIgnoreProperties(value = {"worker"}, allowSetters = true)
	@JsonIgnore
	private Set<Failure> failures; // Kvarovi na kojima je radio
	
	@OneToMany(mappedBy = "author")
	@JsonIgnoreProperties(value = {"author"}, allowSetters = true)
	@JsonIgnore
	private List<Comment> comments; // Komentare koje je napisao

	@OneToMany(mappedBy = "tenat")
	@JsonIgnoreProperties(value = {"tenat"}, allowSetters = true)
	@JsonIgnore
	private List<Notification> notifications; // Obavestenja koja je postavio
	
	@OneToMany(mappedBy = "tenat")
	@JsonIgnoreProperties(value = {"tenat"}, allowSetters = true)
	@JsonIgnore
	private List<Stavka> stavke; // stavke koje je postavio
	
	@OneToMany(mappedBy = "creator")
	@JsonIgnoreProperties(value = {"creator"}, allowSetters = true)
	@JsonIgnore
	private List<Sednica> sednice; // Sednice koje je kreirao
	
	boolean owner; // Da li je vlasnik stana
	private boolean voted; // Da li je glasao za predsednika skupstine stanara
	private boolean tenatsPresident; // Da li je predsednik skupstine stanara
	
	private boolean hasBuilding; // Indicates if the tenat has building or not
	private boolean hasInstitution; // Indicates if the tenat has institution or not
	
	private int votes; // Glasove koje je dobio sa glasanja
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	public List<String> getRoles() {
		return roles;
	}

	public void setRoles(List<String> roles) {
		this.roles = roles;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@JsonIgnore
	@Override
	public boolean isEnabled() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}
	
	@JsonIgnore
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@JsonIgnore
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@JsonIgnore
	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		Collection<GrantedAuthority> authorities = new ArrayList<>();
		for (String role : roles) {
			authorities.add(new SimpleGrantedAuthority(role));
		}
		return authorities;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return username;
	}

	public boolean isHasBuilding() {
		return hasBuilding;
	}

	public void setHasBuilding(boolean hasBuilding) {
		this.hasBuilding = hasBuilding;
	}
	
	public Apartmen getApartmen() {
		return apartmen;
	}

	public void setApartmen(Apartmen apartmen) {
		this.apartmen = apartmen;
	}

	public boolean isOwner() {
		return owner;
	}

	public void setOwner(boolean owner) {
		this.owner = owner;
	}

	public boolean isVoted() {
		return voted;
	}

	public void setVoted(boolean voted) {
		this.voted = voted;
	}

	public boolean isTenatsPresident() {
		return tenatsPresident;
	}

	public void setTenatsPresident(boolean tenatsPresident) {
		this.tenatsPresident = tenatsPresident;
	}
	
	public int getVotes() {
		return votes;
	}

	public void setVotes(int votes) {
		this.votes = votes;
	}

	public Institution getInstitution() {
		return institution;
	}

	public void setInstitution(Institution institution) {
		this.institution = institution;
	}

	public boolean isHasInstitution() {
		return hasInstitution;
	}

	public void setHasInstitution(boolean hasInstitution) {
		this.hasInstitution = hasInstitution;
	}

	public Set<Failure> getFailures() {
		return failures;
	}

	public void setFailures(Set<Failure> failures) {
		this.failures = failures;
	}

	public List<Comment> getComments() {
		return comments;
	}

	public void setComments(List<Comment> comments) {
		this.comments = comments;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

	public List<Stavka> getStavke() {
		return stavke;
	}

	public void setStavke(List<Stavka> stavke) {
		this.stavke = stavke;
	}

	public List<Sednica> getSednice() {
		return sednice;
	}

	public void setSednice(List<Sednica> sednice) {
		this.sednice = sednice;
	}
}
