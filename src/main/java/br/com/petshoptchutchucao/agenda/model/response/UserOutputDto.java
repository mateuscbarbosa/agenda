package br.com.petshoptchutchucao.agenda.model.response;

public class UserOutputDto extends SimplifiedOutputDto {

	private String email;
	
	public UserOutputDto() {}

	public UserOutputDto(String id, String email,  String name) {
		super(id,name);
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	
}
