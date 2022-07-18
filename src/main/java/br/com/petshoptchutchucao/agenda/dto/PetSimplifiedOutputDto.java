package br.com.petshoptchutchucao.agenda.dto;

public class PetSimplifiedOutputDto {

	private String id;
	private String name;
	
	public PetSimplifiedOutputDto() {}

	public PetSimplifiedOutputDto(String id, String name) {
		this.id = id;
		this.name = name;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
		
}
