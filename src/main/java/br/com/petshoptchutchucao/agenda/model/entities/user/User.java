package br.com.petshoptchutchucao.agenda.model.entities.user;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

@Document(collection = "users")
public class User implements UserDetails{

	@Id
	private String id;
	private String email;
	private String password;
	private String name;
	private List<Profile> profiles = new ArrayList<>();
	private Status status;
	
	public User() {}

	public User(String email, String name, List<Profile> profiles, Status status) {
		this.email = email;
		this.name = name;
		this.profiles = profiles;
		this.status = status;
	}

	public User(String email, String password, String name, List<Profile> profiles, Status status) {
		this.email = email;
		this.password = password;
		this.name = name;
		this.profiles = profiles;
		this.status = status;
	}

	public User(String id, String email, String password, String name, List<Profile> profiles, Status status) {
		this.id = id;
		this.email = email;
		this.password = password;
		this.name = name;
		this.profiles = profiles;
		this.status = status;
	}
	
	public void updateInfo(String email, String name, String password, List<Profile> profiles, Status status) {
		this.email = email;
		this.name = name;
		this.password = password;
		this.profiles = profiles;
		this.status = status;
	}

	public void addProfile(Profile profile) {
		this.profiles.add(profile);
	}
	
	public String getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}
	
	public String getPassword() {
		return password;
	}

	public String getName() {
		return name;
	}

	public List<Profile> getProfiles() {
		return profiles;
	}
	
	public Status getStatus() {
		return status;
	}

	public void setId(String id) {
		this.id = id;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProfiles(List<Profile> profiles) {
		this.profiles = profiles;
	}

	public void setStatus(Status status) {
		this.status = status;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return this.profiles;
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		if(this.status.equals(Status.INATIVO)) {
			return false;
		}
		return true;
	}
	
}
