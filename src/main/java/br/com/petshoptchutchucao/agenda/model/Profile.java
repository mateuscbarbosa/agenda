package br.com.petshoptchutchucao.agenda.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "profiles")
public class Profile {

	@Id
	private String id;
	private String name;
	
	public Profile() {};
	
	public Profile(String id, String name) {
		this.id = id;
		this.name = name;
	}
	
	public String getId() {
		return id;
	}
	public String getName() {
		return name;
	}
}
