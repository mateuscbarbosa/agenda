package br.com.petshoptchutchucao.agenda.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.security.core.GrantedAuthority;

@Document(collection = "profiles")
public class Profile implements GrantedAuthority{

	@Id
	private String id;
	private String role;
	private String description;
	
	public Profile() {};
	
	public Profile(String id,String role ,String description) {
		this.id = id;
		this.role = role;
		this.description = description;
	}
	
	@Override
	public String getAuthority() {
		return this.role;
	}
	
	public String getId() {
		return id;
	}
	public String getRole() {
		return role;
	}
	public String getDescription() {
		return description;
	}
	
}
