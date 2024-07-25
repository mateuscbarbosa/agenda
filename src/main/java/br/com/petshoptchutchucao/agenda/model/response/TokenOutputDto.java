package br.com.petshoptchutchucao.agenda.model.response;

public class TokenOutputDto {
	
	private String token;

	public TokenOutputDto(String token) {
		this.token = token;
	}
	
	public String getToken() {
		return token;
	}
}
